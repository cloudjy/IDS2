<span><s:text name="p_altogether" /><b class="orange1"><span
		id="totalData"></span></b>
<s:text name="p_article" /></span>
<span><s:text name="p_first" /><b id="currentWithTotal"></b>
<s:text name="p_page" /></span>
<span><s:text name="p_everypage" /> <select name="pagesize"
	onchange="$p.changepagesize(this.value)">
		<!-- option value="10">10</option -->
		<option value="20">20</option>
		<option value="50">50</option>
		<option value="<%=Integer.MAX_VALUE %>">
			<s:text name="p_all" />
		</option>
</select> </span>
<span><s:text name="p_goto" /> <select name="pageIndex"
	id="pageIndex" onchange="$p.gotoPage(this.value)">
		<option value="1">1</option>
</select> </span>
<img src="${theme}/skins/default/images/pageB1.gif" id="first" />
<img src="${theme}/skins/default/images/pageB2.gif" id="previous" />
<img src="${theme}/skins/default/images/pageB3.gif" id="next" />
<img src="${theme}/skins/default/images/pageB4.gif" id="last" />