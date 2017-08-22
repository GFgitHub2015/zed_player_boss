package com.zed.service.player.es.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexBoost;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.Assert;

import com.google.gson.JsonPrimitive;
import com.zed.common.ConstantType;
import com.zed.common.ErrorCode;
import com.zed.common.exception.AppErrorException;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.domain.player.logicalfile.PlayerVideoResources;
import com.zed.listener.ElasticConfig;
import com.zed.search.service.impl.ElasticsearchServiceImpl;
import com.zed.search.springdata.jest.JestElasticsearchTemplate;
import com.zed.search.springdata.jest.internal.ExtendedSearchResult;
import com.zed.service.common.signature.SignatureService;
import com.zed.service.player.es.PlayerVideoElasticsearch2Service;
import com.zed.service.player.playeruser.HotPlayerUserService;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.ES;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.params.SearchType;

public class PlayerVideoElasticsearch2ServiceImpl extends ElasticsearchServiceImpl implements PlayerVideoElasticsearch2Service {

	@Autowired
	private JestElasticsearchTemplate elasticsearchTemplate;
	
	@Resource(name="jestClient")
	private JestClient jestClient;
	
	@Resource(name="signatureService")
	private SignatureService signatureService;
	
	@Resource(name="hotPlayerUserService")
	private HotPlayerUserService hotPlayerUserService;
	
	@Override
	public void add(PlayerVideoResources playerVideoResources2) {
		elasticsearchTemplate.index(getIndexQuery(playerVideoResources2));
		elasticsearchTemplate.refresh(PlayerVideoResources.class);
	}

	@Override
	public void add(List<PlayerVideoResources> playerVideoResources2List) {
		elasticsearchTemplate.bulkIndex(getIndexQueries(playerVideoResources2List));
		elasticsearchTemplate.refresh(PlayerVideoResources.class);
	}

	@Override
	public List<PlayerVideoResources> find(Page<PlayerVideoResources> page, String fileName, String userId, String dimension) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", ConstantType.CommonType.START.getStatus());
		parameter.put("shareStatus", PlayerLogicalFile.ShareStatus.SHARE.getStatus());
		parameter.put("fileName", fileName);
		if (!StringUtils.isEmpty(userId)) {
			parameter.put("userId", userId);
		}
		if (!StringUtils.isEmpty(dimension)) {
			parameter.put("dimension", dimension);
		}
		page.setParamsMap(parameter);
		return find(page);
	}

	@Override
	public List<PlayerVideoResources> find(Page<PlayerVideoResources> page) {
		List<PlayerVideoResources> result = new ArrayList<PlayerVideoResources>();
		if(page != null){
			//是否只查询记录数
			Map<String, Object> param = page.getParamsMap();
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			if (param!=null&&param.get("fileName")!=null) {
				String fileName = param.get("fileName").toString();
				if (!StringUtils.isEmpty(fileName)) {
					String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?～！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
					Pattern p = Pattern.compile(regEx);     
					Matcher m = p.matcher(fileName);
					fileName = m.replaceAll(" ").trim();
					if (!StringUtils.isEmpty(fileName)) {
						queryBuilder.must(QueryBuilders.matchQuery("fileName",fileName));
					}
				}
			}
			BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
			if (param!=null) {
				if (param.get("status")!=null) {
					Integer status = Integer.valueOf(param.get("status").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("status", status);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("shareStatus")!=null) {
					Integer shareStatus = Integer.valueOf(param.get("shareStatus").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("shareStatus", shareStatus);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("userId")!=null) {
					String[] userIdArray = param.get("userId").toString().split(",");
					BoolQueryBuilder shouldQueryBuilder = QueryBuilders.boolQuery();
					for (String userId : userIdArray) {
						if (!StringUtils.isBlank(userId)) {
							shouldQueryBuilder.should(QueryBuilders.matchQuery("userId", userId));
						}
					}
					queryBuilder.must(shouldQueryBuilder);
				}
				if (param.get("dimension")!=null) {
					Integer dimension = Integer.valueOf(param.get("dimension").toString());
					if (dimension==2) {
						TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("dimension", dimension);
						filterBuilder.filter(termQueryBuilder);
					}
				}
			}
			int pageNo = (int) (page.getPageNo())-1;
			int size = (int) (page.getPageSize());
			PageRequest pageRequest = new PageRequest(pageNo,size);
			
			SearchQuery searchQuery = new NativeSearchQuery(queryBuilder, filterBuilder);
			searchQuery.setPageable(pageRequest);
			org.springframework.data.domain.Page<PlayerVideoResources> results = elasticsearchTemplate.queryForPage(searchQuery, PlayerVideoResources.class);
			if (results!=null&&results.hasContent()) {
				result.addAll(results.getContent());
			}
		}
		return result;
	}

	@Override
	public PlayerVideoResources findByFileId(String fileId) {
		GetQuery getQuery = new GetQuery();
		getQuery.setId(fileId);
		return elasticsearchTemplate.queryForObject(getQuery, PlayerVideoResources.class);
	}

	@Override
	public Boolean deleteIndex() {
		if (elasticsearchTemplate.indexExists(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME)) {
			elasticsearchTemplate.deleteIndex(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME);
		}
		return !elasticsearchTemplate.indexExists(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME);
	}

	@Override
	public Boolean createIndexAndTypeWithMapping() {
		try {
			if (!elasticsearchTemplate.indexExists(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME)) {
				elasticsearchTemplate.createIndex(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME);
			}
			
			if(!elasticsearchTemplate.typeExists(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME, ES.ESProp.TYPE_PLAYER_LOGICAL_FILE_NAME)){
				elasticsearchTemplate.putMapping(Class.forName(ElasticConfig.getString("es.index.mapping.logical.file.class")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AppErrorException(ErrorCode.CODE_ERROR.getCode(),
					ErrorCode.CODE_ERROR.getMessage());
		}
		return elasticsearchTemplate.typeExists(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME, ES.ESProp.TYPE_PLAYER_LOGICAL_FILE_NAME);
	}
	
	private IndexQuery getIndexQuery(PlayerVideoResources playerVideoResources) {  
        return new IndexQueryBuilder().withId(playerVideoResources.getSearchId()).withObject(playerVideoResources).build();  
    }  
	
	private List<IndexQuery> getIndexQueries(List<PlayerVideoResources> playerVideoResourcesList) {  
        List<IndexQuery> indexQueries = new ArrayList<IndexQuery>();  
        for (PlayerVideoResources playerVideoResources : playerVideoResourcesList) {  
            indexQueries.add(new IndexQueryBuilder().withId(playerVideoResources.getSearchId()).withObject(playerVideoResources).build());  
        }  
        return indexQueries;  
    }

	@Override
	public void deleteByIds(String... fileIds) {
		for (String fileId : fileIds) {
			elasticsearchTemplate.delete(ES.ESProp.INDEX_PLAYER_LOGICAL_FILE_NAME, ES.ESProp.TYPE_PLAYER_LOGICAL_FILE_NAME, fileId);
		}
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) {
		if(page != null){
			//是否只查询记录数
			Map<String, Object> param = page.getParamsMap();
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			if (param!=null&&param.get("fileName")!=null) {
				String fileName = param.get("fileName").toString();
				if (!StringUtils.isEmpty(fileName)) {
					String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?～！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
					Pattern p = Pattern.compile(regEx);     
					Matcher m = p.matcher(fileName);
					fileName = m.replaceAll(" ").trim();
					if (!StringUtils.isEmpty(fileName)) {
						BoolQueryBuilder bb = QueryBuilders.boolQuery();
						bb.must(QueryBuilders.matchPhraseQuery("fileName", fileName).boost(99999999f));
						queryBuilder.should(bb);
						
						MatchQueryBuilder mqb = new MatchQueryBuilder("fileName", fileName);
						mqb.operator(Operator.OR);
						mqb.boost(4444444f);
						queryBuilder.must(mqb);
					}
				}
			}
			Map<String, Object> shareCountsWithCountryCodes = hotPlayerUserService.getShareCountsWithCountryCodes();
			if (!shareCountsWithCountryCodes.isEmpty()) {
				for (Map.Entry<String, Object> entry : shareCountsWithCountryCodes.entrySet()) {
					Double score = (Double) entry.getValue();
					if (param.get("countryCode")!=null) {
						String countryCode = param.get("countryCode").toString();
						if (StringUtils.isNotBlank(countryCode)&&countryCode.equals(entry.getKey())) {
							score = 999999999f-score;
						}
					}
					queryBuilder.should(QueryBuilders.matchPhraseQuery("countryCode", entry.getKey()). boost(score.floatValue()));
				}
			}
			BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
			if (param!=null) {
				if (param.get("status")!=null) {
					Integer status = Integer.valueOf(param.get("status").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("status", status);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("shareStatus")!=null) {
					Integer shareStatus = Integer.valueOf(param.get("shareStatus").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("shareStatus", shareStatus);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("userId")!=null) {
					String[] userIdArray = param.get("userId").toString().split(",");
					BoolQueryBuilder shouldQueryBuilder = QueryBuilders.boolQuery();
					for (String userId : userIdArray) {
						if (!StringUtils.isBlank(userId)) {
							shouldQueryBuilder.should(QueryBuilders.matchQuery("userId", userId));
						}
					}
					queryBuilder.must(shouldQueryBuilder);
				}
				if (param.get("dimension")!=null) {
					Integer dimension = Integer.valueOf(param.get("dimension").toString());
					if (dimension==2) {
						TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("dimension", dimension);
						filterBuilder.filter(termQueryBuilder);
					}
				}
				if (param.get("fileId")!=null) {
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("fileId", param.get("fileId").toString());
					filterBuilder.filter(termQueryBuilder);
				}
			}
			int pageNo = (int) (page.getPageNo())-1;
			int size = (int) (page.getPageSize());
			PageRequest pageRequest = new PageRequest(pageNo,size);
			
			SearchQuery searchQuery = new NativeSearchQuery(queryBuilder, filterBuilder);
			searchQuery.setPageable(pageRequest);
			org.springframework.data.domain.Page<PlayerVideoResources> results = elasticsearchTemplate.queryForPage(searchQuery, PlayerVideoResources.class);
			if (results!=null&&results.hasContent()) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				List<PlayerVideoResources> list = results.getContent();
				if (list != null) {
					for (PlayerVideoResources playerVideoResources : list) {
						if (!CommUtil.isEmpty(playerVideoResources.getImgUrl())) {
							playerVideoResources.setImgUrl(signatureService.signaturePic(playerVideoResources.getImgUrl()));
						}
						mapList.add(playerVideoResources.forMap());
					}
					page.setTotalCount(results.getTotalElements());
//					page.setTotalPage(results.getTotalPages());
					page.setObjectResult(mapList);
				}
			}
		}
		return page;
	}

	private SearchSourceBuilder prepareSearch(Query query) {
		Assert.notNull(query.getIndices(), "No index defined for Query");
		Assert.notNull(query.getTypes(), "No type defined for Query");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		int startRecord = 0;

		if (query.getPageable() != null) {
			startRecord = query.getPageable().getPageNumber() * query.getPageable().getPageSize();
			searchSourceBuilder.size(query.getPageable().getPageSize());
		}
		searchSourceBuilder.from(startRecord);

		if (!query.getFields().isEmpty()) {
			searchSourceBuilder.fields(query.getFields());
		}

		if (query.getSort() != null) {
			for (Sort.Order order : query.getSort()) {
				searchSourceBuilder.sort(order.getProperty(), order.getDirection() == Sort.Direction.DESC ? SortOrder.DESC : SortOrder.ASC);
			}
		}

		if (query.getMinScore() > 0) {
			searchSourceBuilder.minScore(query.getMinScore());
		}
		return searchSourceBuilder;
	}

	private SearchResult doSearch(SearchSourceBuilder searchSourceBuilder, SearchQuery searchQuery) {
		if (searchQuery.getFilter() != null) {
			searchSourceBuilder.postFilter(searchQuery.getFilter());
		}

		if (!isEmpty(searchQuery.getElasticsearchSorts())) {
			for (SortBuilder sort : searchQuery.getElasticsearchSorts()) {
				searchSourceBuilder.sort(sort);
			}
		}

		if (searchQuery.getHighlightFields() != null) {
			HighlightBuilder highlighter = searchSourceBuilder.highlighter();
			for (HighlightBuilder.Field highlightField : searchQuery.getHighlightFields()) {
				highlighter.field(highlightField);
			}
		}

		if (!isEmpty(searchQuery.getAggregations())) {
			for (AbstractAggregationBuilder aggregationBuilder : searchQuery.getAggregations()) {
				searchSourceBuilder.aggregation(aggregationBuilder);
			}
		}

		if (!isEmpty(searchQuery.getIndicesBoost())) {
			for (IndexBoost indexBoost : searchQuery.getIndicesBoost()) {
				searchSourceBuilder.indexBoost(indexBoost.getIndexName(), indexBoost.getBoost());
			}
		}

		if (!searchQuery.getScriptFields().isEmpty()) {
			searchSourceBuilder.field("_source");
			for (ScriptField scriptedField : searchQuery.getScriptFields()) {
				searchSourceBuilder.scriptField(scriptedField.fieldName(), scriptedField.script());
			}
		}

		return executeSearch(searchQuery, searchSourceBuilder.query(searchQuery.getQuery()));
	}

	private SearchResult executeSearch(Query query, SearchSourceBuilder request) {

		Search.Builder search = new Search.Builder(request.toString());
		if (query != null) {
			search.
					addType(query.getTypes()).
					addIndex(query.getIndices()).
					setSearchType(SearchType.valueOf(query.getSearchType().name()));
		}

		return new ExtendedSearchResult(execute(search.build()));
	}
	
	private <T extends JestResult> T execute(Action<T> action) {
		try {
			T result = jestClient.execute(action);
			if (!result.isSucceeded()) {

				String errorMessage = String.format("Cannot execute jest action , response code : %s , error : %s , message : %s", result.getResponseCode(), result.getErrorMessage(), getMessage(result));

				if(isSuccessfulResponse(result.getResponseCode())) {
				} else {
					throw new ElasticsearchException(errorMessage);
				}
			}

			return result;

		} catch (IOException e) {
			throw new ElasticsearchException("failed to execute action", e);
		}
	}

	private static boolean isSuccessfulResponse(int statusCode) {
		return statusCode < 300 || statusCode == 404;
	}
	
	private <T extends JestResult> String getMessage(T result) {
		if (result.getJsonObject() == null) {
			return null;
		}
		JsonPrimitive message = result.getJsonObject().getAsJsonPrimitive("message");
		if (message == null) {
			return null;
		}
		return message.getAsString();
	}
}
