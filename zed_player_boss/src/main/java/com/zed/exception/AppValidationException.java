package	com.zed.exception;


public class AppValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public String gs_Module;
	public String gs_Code;
	public String[] gsA_Params;
	public String gs_PrevLink;
	public String gs_Msg;

	public AppValidationException(String as_Msg) {
		super(as_Msg);
		this.gs_Msg = as_Msg;
	}
	
	public AppValidationException(String as_Code, String as_PrevLink) {
		gs_Code = as_Code;
		gs_PrevLink = as_PrevLink;
	}

	public AppValidationException(String as_Module, String as_Code, String as_PrevLink) {
		gs_Module = as_Module;
		gs_Code = as_Code;
		gs_PrevLink = as_PrevLink;
	}

	public AppValidationException(String as_Module, String as_Code, String[] asA_Params, String as_PrevLink) {
		gs_Module = as_Module;
		gs_Code = as_Code;
		gsA_Params = asA_Params;
		gs_PrevLink = as_PrevLink;
	}

	public AppValidationException(String as_Module, String as_Code, String as_PrevLink, String as_Msg) {
		gs_Module = as_Module;
		gs_Code = as_Code;
		gs_PrevLink = as_PrevLink;
		gs_Msg = as_Msg;
	}

}
