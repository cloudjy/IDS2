function login() {
	var $custid = $("input[name='domain']");
	var $user = $("input[name='uid']");
	var $pass = $("input[name='pwd']");
	var message = "";

	$("#bubble_tooltip").hide(); 

	if ($user.val() == "") {
		message = message + "请输入登录用户名,";
		$user.val("");
		return;
	}
	if ($pass.val() == "") {
		message = message + "请输入登录密码";
		$pass.val("");
		return;
	}
	if ($custid.val() == "") {
		message = message + "请输入登录客户域,";
		$custid.val("");
		return;
	}

	$.post('login!userValidate', {
				"shortName" : $custid.val(),
				"user.userName" : $user.val(),
				"user.password" : $("input[name=pwd]").val(),
				"loginVerifyCode" : $("input[name=code]").val(),
				"autoSave" : $("input[name=autoSave]").attr("checked")? true : false,
				"autoLogin" : $("input[name=autoLogin]").attr("checked")? true : false,
				"domain_login" : $("input[name=domainInput]").val() 
			}, function(result) {
				if (result.success) {
					window.location.href = "index";
				} else {
					refresh();
					if (result.field != undefined) {
						if (result.field.loginVerifyCode != undefined) {  
							showErrorMessage(result.field.loginVerifyCode);
						}
					} else if (result.data  && result.message) { 
						showErrorMessage(result.message);
					} else if (result.message) {  
						showErrorMessage(result.message);
					}
				}
			});
}

function refresh() {
	var timenow = new Date().getTime();
	$(".code").attr("src", "login!verifyCode.action?time=" + timenow);
}

function showErrorMessage(text){  
	$("#bubble_tooltip_content").text(text); 
	if ($("#bubble_tooltip").is(":hidden")) {
		$("#bubble_tooltip").toggle("fast"); 
	}
}	

function hideErrorBox() {
	if ($("#bubble_tooltip").is(":visible")) {
		$("#bubble_tooltip").hide("fast"); 
	}
}