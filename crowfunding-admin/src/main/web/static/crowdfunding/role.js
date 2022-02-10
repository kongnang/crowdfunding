function generatePage() {
// 1.获取分页数据
    var pageInfo = getPageInfoRemote();
// 2.填充表格
    fillTableBody(pageInfo);
}

function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        "url": "http://localhost:8080/admin/role/info.json",
        "type":"post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async":false,
        "dataType":"json"
    });
    console.log(ajaxResult);

    var statusCode = ajaxResult.status;
    if(statusCode != 200) {
        layer.msg("FAILED ! CODE ="+statusCode+" MESSAGE ="+ajaxResult.statusText);
        return null;
    }
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;
    if(result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
    var pageInfo = resultEntity.data;
    return pageInfo;
}

function fillTableBody(pageInfo) {

    $("#rolePageBody").empty();

    $("#Pagination").empty();

    if(pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>Sorry!Data Not Exist!</td></tr>");
        return ;
    }

    for(var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>"+(i+1)+"</td>";
        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>"+roleName+"</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}


function generateNavigator(pageInfo) {

    var totalRecord = pageInfo.total;

    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "prev ",
        "next_text": "next "
    }
// 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

function paginationCallBack(pageIndex, jQuery) {

    window.pageNum = pageIndex + 1;
    generatePage();
    return false;
}

// 声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {
    $("#confirmModal").modal("show");
    $("#roleNameDiv").empty();
    window.roleIdArray = [];
    for(var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName+"<br/>");
        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }
}