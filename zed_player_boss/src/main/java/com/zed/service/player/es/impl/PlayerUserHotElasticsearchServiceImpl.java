package com.zed.service.player.es.impl;

import static org.springframework.util.CollectionUtils.isEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
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
import com.zed.common.ErrorCode;
import com.zed.common.exception.AppErrorException;
import com.zed.domain.player.playeruser.PlayerUserHot;
import com.zed.listener.ElasticConfig;
import com.zed.search.service.impl.ElasticsearchServiceImpl;
import com.zed.search.springdata.jest.JestElasticsearchTemplate;
import com.zed.search.springdata.jest.internal.ExtendedSearchResult;
import com.zed.service.player.es.PlayerUserHotElasticsearchService;
import com.zed.system.page.Page;
import com.zed.util.ES;

import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.TermsAggregation.Entry;
import io.searchbox.params.SearchType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PlayerUserHotElasticsearchServiceImpl extends ElasticsearchServiceImpl implements PlayerUserHotElasticsearchService {
	@Autowired
	private JestElasticsearchTemplate elasticsearchTemplate;
	
	@Resource(name="jestClient")
	private JestClient jestClient;
	
	@Override
	public void add(PlayerUserHot playerUserHot) {
		elasticsearchTemplate.index(getIndexQuery(playerUserHot));
		elasticsearchTemplate.refresh(PlayerUserHot.class);
	}

	@Override
	public void add(List<PlayerUserHot> playerUserHotList) {
		elasticsearchTemplate.bulkIndex(getIndexQueries(playerUserHotList));
		elasticsearchTemplate.refresh(PlayerUserHot.class);

	}

	@Override
	public List<PlayerUserHot> find(Page<PlayerUserHot> page, String countryCode, String userId, String status) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(status)) {
			parameter.put("status", status);
		}
		if (!StringUtils.isEmpty(countryCode)) {
			parameter.put("countryCode", countryCode);
		}
		if (!StringUtils.isEmpty(userId)) {
			parameter.put("userId", userId);
		}
		page.setParamsMap(parameter);
		return find(page);
	}

	@Override
	public List<PlayerUserHot> find(Page<PlayerUserHot> page) {
		List<PlayerUserHot> result = new ArrayList<PlayerUserHot>();
		if(page != null){
			//是否只查询记录数
			Map<String, Object> param = page.getParamsMap();
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
			if (param!=null) {
				if (param.get("status")!=null) {
					Integer status = Integer.valueOf(param.get("status").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("status", status);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("countryCode")!=null) {
					Integer countryCode = Integer.valueOf(param.get("countryCode").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("countryCode", countryCode);
					filterBuilder.filter(termQueryBuilder);
					
				}
				if (param.get("userId")!=null) {
					String userId = param.get("userId").toString();
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", userId);
					filterBuilder.filter(termQueryBuilder);
				}
			}
			int pageNo = (int) (page.getPageNo())-1;
			int size = (int) (page.getPageSize());
			PageRequest pageRequest = new PageRequest(pageNo,size);
			
			SearchQuery searchQuery = new NativeSearchQuery(queryBuilder, filterBuilder);
			searchQuery.setPageable(pageRequest);
			org.springframework.data.domain.Page<PlayerUserHot> results = elasticsearchTemplate.queryForPage(searchQuery, PlayerUserHot.class);
			if (results!=null&&results.hasContent()) {
				result.addAll(results.getContent());
			}
		}
		return result;
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) {
		if(page != null){
			//是否只查询记录数
			Map<String, Object> param = page.getParamsMap();
			BoolQueryBuilder boostqueryBuilder = QueryBuilders.boolQuery();
			BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
			if (param!=null) {
				if (param.get("status")!=null) {
					Integer status = Integer.valueOf(param.get("status").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("status", status);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("userId")!=null) {
					String userId = param.get("userId").toString();
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", userId);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("userRoleStatus")!=null) {
					Integer userRoleStatus = Integer.valueOf(param.get("userRoleStatus").toString());
					TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userRoleStatus", userRoleStatus);
					filterBuilder.filter(termQueryBuilder);
				}
				if (param.get("userIdNotToMatch")!=null) {
					String userIdNotToMatch = param.get("userIdNotToMatch").toString();
					String[] userIdNotToMatchArray = userIdNotToMatch.split(",");
					BoolQueryBuilder mustNotMatchQuery = QueryBuilders.boolQuery();
					for (String uid : userIdNotToMatchArray) {
						mustNotMatchQuery.mustNot(QueryBuilders.matchQuery("userId", uid));
					}
					boostqueryBuilder.filter(mustNotMatchQuery);
				}
				boostqueryBuilder.filter(filterBuilder);
			}
			BoolQueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery();
			NativeSearchQuery searchQuery = new NativeSearchQuery(aggsQueryBuilder.must(QueryBuilders.matchAllQuery()), null);
			PageRequest pageRequest = new PageRequest(0,500);
			SumBuilder subAggs = AggregationBuilders.sum("sum_share_count").field("shareCount");
			TermsBuilder parentAggs = AggregationBuilders.terms("country_codes").field("countryCode").size(500).order(Terms.Order.aggregation("sum_share_count", Boolean.FALSE));
			if (subAggs != null) {
				parentAggs.subAggregation(subAggs);
			}
			searchQuery.setPageable(pageRequest);
			
			
			SearchSourceBuilder ssb = SearchSourceBuilder.searchSource();
			ssb.aggregation(parentAggs);
			
			SearchResult searchResult = doSearch(ssb, searchQuery);
			List<Entry> entryList = searchResult.getAggregations().getTermsAggregation("country_codes").getBuckets();
			Map<String, Object> linkedMap = new LinkedHashMap<>();
			for (Entry entry : entryList) {
				String key  = entry.getKey();
				Double count = entry.getSumAggregation("sum_share_count").getSum();
				linkedMap.put(key, count);
			}
			for (Map.Entry<String,Object> entry : linkedMap.entrySet()) {
				Double score = (Double) entry.getValue();
				if (param.get("countryCode")!=null) {
					String countryCode = param.get("countryCode").toString();
					if (StringUtils.isNotBlank(countryCode)&&countryCode.equals(entry.getKey())) {
						score = 999999999f-score;
					}
				}
				boostqueryBuilder.should(QueryBuilders.matchQuery("countryCode", entry.getKey()).boost(score.floatValue()));
			}
			SearchQuery boostQuery = new NativeSearchQuery(boostqueryBuilder, null);
			int	pageNo = (int) (page.getPageNo())-1;
			int	size = (int) (page.getPageSize());
			PageRequest boostPageRequest = new PageRequest(pageNo,size);
			boostQuery.setPageable(boostPageRequest);
			org.springframework.data.domain.Page<PlayerUserHot> results = elasticsearchTemplate.queryForPage(boostQuery, PlayerUserHot.class);
			
			if (results!=null&&results.hasContent()) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				List<PlayerUserHot> list = results.getContent();
				if (list != null) {
					for (PlayerUserHot playerUserHot : list) {
						mapList.add(playerUserHot.forMap());
					}
					page.setTotalCount(results.getTotalElements());
					page.setObjectResult(mapList);
				}
			}
			
		}
		return page;
	}

	@Override
	public PlayerUserHot findByUserId(String userId) {
		GetQuery getQuery = new GetQuery();
		getQuery.setId(userId);
		return elasticsearchTemplate.queryForObject(getQuery, PlayerUserHot.class);
	}

	@Override
	public void deleteByIds(String... userIds) {
		for (String userId : userIds) {
			elasticsearchTemplate.delete(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME, ES.ESProp.TYPE_PLAYER_USER_HOT_NAME, userId);
		}
	}

	@Override
	public Boolean deleteIndex() {
		if (elasticsearchTemplate.indexExists(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME)) {
			elasticsearchTemplate.deleteIndex(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME);
		}
		return !elasticsearchTemplate.indexExists(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME);
	}

	@Override
	public Boolean createIndexAndTypeWithMapping() {
		try {
			if (!elasticsearchTemplate.indexExists(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME)) {
				elasticsearchTemplate.createIndex(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME);
			}
			
			if(!elasticsearchTemplate.typeExists(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME, ES.ESProp.TYPE_PLAYER_USER_HOT_NAME)){
				elasticsearchTemplate.putMapping(Class.forName(ElasticConfig.getString("es.index.mapping.player.hotuser.class")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new AppErrorException(ErrorCode.CODE_ERROR.getCode(),
					ErrorCode.CODE_ERROR.getMessage());
		}
		return elasticsearchTemplate.typeExists(ES.ESProp.INDEX_PLAYER_USER_HOT_NAME, ES.ESProp.TYPE_PLAYER_USER_HOT_NAME);
	}
	
	private IndexQuery getIndexQuery(PlayerUserHot playerUserHot) {  
        return new IndexQueryBuilder().withId(playerUserHot.getSearchId()).withObject(playerUserHot).build();  
    }  
	
	private List<IndexQuery> getIndexQueries(List<PlayerUserHot> playerUserHotList) {  
        List<IndexQuery> indexQueries = new ArrayList<IndexQuery>();  
        for (PlayerUserHot playerUserHot : playerUserHotList) {  
            indexQueries.add(new IndexQueryBuilder().withId(playerUserHot.getSearchId()).withObject(playerUserHot).build());  
        }  
        return indexQueries;  
    }
	//方案一
	@Override
	public void findByAggs(Map<String, Object> param) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		int pageNo = 0;
		int size = 100;
		NativeSearchQuery searchQuery = new NativeSearchQuery(queryBuilder.must(QueryBuilders.matchAllQuery()), null);
		PageRequest pageRequest = new PageRequest(pageNo,size);
		SumBuilder subAggs = AggregationBuilders.sum("sum_share_count").field("shareCount");
		TermsBuilder parentAggs = AggregationBuilders.terms("country_codes").field("countryCode").size(100).order(Terms.Order.aggregation("sum_share_count", Boolean.FALSE));
		if (subAggs != null) {
			parentAggs.subAggregation(subAggs);
		}
		searchQuery.setPageable(pageRequest);
		
		
		System.out.println("###################################################################################################");
		SearchSourceBuilder ssb = SearchSourceBuilder.searchSource();
		ssb.aggregation(parentAggs);
		
		SearchResult searchResult = doSearch(ssb, searchQuery);
		List<Entry> entryList = searchResult.getAggregations().getTermsAggregation("country_codes").getBuckets();
		Map<String, Object> linkedMap = new LinkedHashMap<>();
		for (Entry entry : entryList) {
			String key  = entry.getKey();
			Double count = entry.getSumAggregation("sum_share_count").getSum();
			System.out.println("key ===>"+key+",        shareCounts====>"+count);
			linkedMap.put(key, count);
		}
		System.out.println("==========================================");
		System.out.println(linkedMap.toString());
		System.out.println("==========================================");
		System.out.println("###################################################################################################");
		BoolQueryBuilder boostqueryBuilder = QueryBuilders.boolQuery();
		for (Map.Entry<String,Object> entry : linkedMap.entrySet()) {
			Double score = (Double) entry.getValue();
//			boostqueryBuilder.filter(TermsBuilders)
			boostqueryBuilder.should(QueryBuilders.matchQuery("countryCode", entry.getKey()).boost(score.floatValue()));
		}
		SearchQuery boostQuery = new NativeSearchQuery(boostqueryBuilder, null);
		PageRequest boostPageRequest = new PageRequest(pageNo,size);
		boostQuery.setPageable(boostPageRequest);
		org.springframework.data.domain.Page<PlayerUserHot> results = elasticsearchTemplate.queryForPage(boostQuery, PlayerUserHot.class);
		
		if (results!=null&&results.hasContent()) {
			List<PlayerUserHot> list = results.getContent();
			if (list != null) {
				for (PlayerUserHot playerUserHot : list) {
					System.out.println(playerUserHot);
				}
			}
		}
		
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

	@Override
	public void findByFunctionScore(Map<String, Object> param) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		int pageNo = 0;
		int size = 100;
		NativeSearchQuery searchQuery = new NativeSearchQuery(queryBuilder.must(QueryBuilders.matchAllQuery()), null);
		PageRequest pageRequest = new PageRequest(pageNo,size);
		SumBuilder subAggs = AggregationBuilders.sum("sum_share_count").field("shareCount");
		TermsBuilder parentAggs = AggregationBuilders.terms("country_codes").field("countryCode").size(100).order(Terms.Order.aggregation("sum_share_count", Boolean.FALSE));
		if (subAggs != null) {
			parentAggs.subAggregation(subAggs);
		}
		searchQuery.setPageable(pageRequest);
		
		
		SearchSourceBuilder ssb = SearchSourceBuilder.searchSource();
		ssb.aggregation(parentAggs);
		
		SearchResult searchResult = doSearch(ssb, searchQuery);
		List<Entry> entryList = searchResult.getAggregations().getTermsAggregation("country_codes").getBuckets();
		Map<String, Double> linkedMap = new LinkedHashMap<String, Double>();
		Set<String> countryCodeSet = new HashSet<String>();
		for (Entry entry : entryList) {
			String key  = entry.getKey();
			countryCodeSet.add(key);
			Double count = entry.getSumAggregation("sum_share_count").getSum();
			System.out.println("key ===>"+key+",        shareCounts====>"+count);
			linkedMap.put(key, count);
		}
		System.out.println(linkedMap.toString());
		
//		JSONObject scription = new JSONObject();
//		JSONArray js = new JSONArray();
	/*	BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
		FunctionScoreQueryBuilder fsqb = QueryBuilders.functionScoreQuery();
		ScoreFunctionBuilder sfb = new ScoreFunctionBuilder();
		for (Map.Entry<String, Double> entry : linkedMap.entrySet()) {
//			JSONObject jo = new JSONObject();
			TermQueryBuilder tqb = new TermQueryBuilder("countryCode",entry.getKey());
			tqb.boost(entry.getValue().floatValue());
			QueryBuilders.boolQuery().filter(tqb);
			fsqb.add(filter, scoreFunctionBuilder)
//			filterQueryBuilder.
			JSONObject jo1 = new JSONObject();
			JSONObject jo2 = new JSONObject();
			jo2.put("countryCode", entry.getKey());
			jo1.put("term", jo2.toString());
			jo.put("filter", jo1.toString());
			jo.put("weight", entry.getValue().floatValue());
			js.add(jo.toString());
		}
//		scription.put("functions", js.toArray());
//		System.out.println(QueryBuilders.termsQuery("countryCode", countryCodeSet).toString());
//		script.put("filter", QueryBuilders.termsQuery("countryCode", countryCodeSet).toString());
//		System.out.println(script.toString());
//		Script script = new Script(scription.toString());
		System.out.println(fsqb.toString());
		SearchQuery boostQuery = new NativeSearchQuery(fsqb, null);
		PageRequest boostPageRequest = new PageRequest(pageNo,size);
		boostQuery.setPageable(boostPageRequest);
		org.springframework.data.domain.Page<PlayerUserHot> results = elasticsearchTemplate.queryForPage(boostQuery, PlayerUserHot.class);
		
		if (results!=null&&results.hasContent()) {
			List<PlayerUserHot> list = results.getContent();
			if (list != null) {
				for (PlayerUserHot playerUserHot : list) {
					System.out.println(playerUserHot);
				}
			}
		}*/
	}

	@Override
	public Map<String, Object> getShareCountsWithCountryCodes() {
		BoolQueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery();
		NativeSearchQuery searchQuery = new NativeSearchQuery(aggsQueryBuilder.must(QueryBuilders.matchAllQuery()), null);
		PageRequest pageRequest = new PageRequest(0,500);
		SumBuilder subAggs = AggregationBuilders.sum("sum_share_count").field("shareCount");
		TermsBuilder parentAggs = AggregationBuilders.terms("country_codes").field("countryCode").size(500).order(Terms.Order.aggregation("sum_share_count", Boolean.FALSE));
		if (subAggs != null) {
			parentAggs.subAggregation(subAggs);
		}
		searchQuery.setPageable(pageRequest);
		
		
		SearchSourceBuilder ssb = SearchSourceBuilder.searchSource();
		ssb.aggregation(parentAggs);
		
		SearchResult searchResult = doSearch(ssb, searchQuery);
		List<Entry> entryList = searchResult.getAggregations().getTermsAggregation("country_codes").getBuckets();
		Map<String, Object> linkedMap = new LinkedHashMap<>();
		for (Entry entry : entryList) {
			String key  = entry.getKey();
			Double count = entry.getSumAggregation("sum_share_count").getSum();
			linkedMap.put(key, count);
		}
		return linkedMap;
	}
}
