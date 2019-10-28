<ul class="nav nav-tabs">
    <li class="active"><a data-tab href="#tabContent1">人才简历</a></li>
    <li><a data-tab href="#tabContent2">招聘信息</a></li>
</ul>
<div class="tab-content">
    <div class="tab-pane active" id="tabContent1">
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>意向岗位</th>
                    <th>简历数量</th>
                </tr>
                </thead>
                <tbody>
                <#list resume?keys as key>
                    <tr>
                        <td>${key}</td>
                        <td>${resume["${key}"]}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="tab-pane" id="tabContent2">
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>招聘岗位</th>
                    <th>招聘数量</th>
                </tr>
                </thead>
                <tbody>
                <#list recruitment?keys as key>
                    <tr>
                        <td>${key}</td>
                        <td>${recruitment["${key}"]}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>