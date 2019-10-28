<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th colspan="4">企业信息</th>
        </tr>
        <tr>
            <th>新增企业数</th>
            <th>已认证企业数</th>
            <th>已推荐企业数</th>
            <th>已冻结企业数</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${jobStatistics.enterpriseNumber}</td>
            <td>${jobStatistics.verifyEnterpriseNumber}</td>
            <td>${jobStatistics.frozenEnterpriseNumber}</td>
            <td>${jobStatistics.frozenEnterpriseNumber}</td>
        </tr>
        </tbody>
    </table>
</div>
<br/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th colspan="3">简历信息</th>
        </tr>
        <tr>
            <th>新增简历数</th>
            <th>已发布简历数</th>
            <th>已冻结简历数</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${jobStatistics.resumeNumber}</td>
            <td>${jobStatistics.publishResumeNumber}</td>
            <td>${jobStatistics.frozenResumeNumber}</td>
        </tr>
        </tbody>
    </table>
</div>
<br/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th colspan="3">招聘信息</th>
        </tr>
        <tr>
            <th>新增招聘信息数</th>
            <th>已发布招聘信息数</th>
            <th>已推荐招聘信息数</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${jobStatistics.recruitmentNumber}</td>
            <td>${jobStatistics.publishRecruitmentNumber}</td>
            <td>${jobStatistics.recommendRecruitmentNumber}</td>
        </tr>
        </tbody>
    </table>
</div>
<br/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th colspan="3">其他</th>
        </tr>
        <tr>
            <th>新增证书数</th>
            <th>已关注简历</th>
            <th>已关注招聘信息</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${jobStatistics.certNumber}</td>
            <td>${jobStatistics.concernRecruitmentNumber}</td>
            <td>${jobStatistics.concernResume}</td>
        </tr>
        </tbody>
    </table>
</div>