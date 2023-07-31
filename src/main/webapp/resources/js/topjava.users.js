const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(userAjaxUrl, updateTableByData);
    }
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function checkEnabled(checkbox) {
    let closestTableRow = $(checkbox).closest('tr');
    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl + closestTableRow.attr("id") + '/enabled?enabled=' + checkbox.checked
    })
        .done(function () {
            successNoty("User " + (checkbox.checked ? 'enabled' : 'disabled'));
        })
        .fail(function () {
            checkbox.checked = !checkbox.checked;
        })
        .always(function () {
            closestTableRow.attr("data-user-enabled", checkbox.checked);
        });
}