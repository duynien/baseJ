$(document).ready(function () {
    $(function (){
        getType();
    })
    function getType() {
        $.ajax({
            url: "http://localhost:8080/type-tour?page=0&limit=10",
            type: 'GET',
            dataType: 'json',
            success: function (types) {
                appendTypes(types);
            }
        });
    }
    function appendTypes(types) {
        var dataHtml = "";
        $.each(types.data, function (index, country) {
            dataHtml += '<a href="" class="btn-list">'+ country.name +'</a>';
        });
        console.log(dataHtml)
        $(".list-tab").append(dataHtml);
    }
});