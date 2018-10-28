 $(document).ready(function () {

/**
 * 导航栏当前页面高亮显示
 */
    $("#index-body li.menu-list-index").addClass("current");
    $("#company-body li.menu-list-company").addClass("current");
    $("#mx2-body li.menu-list-mx2").addClass("current");
    $("#mx3-body li.menu-list-mx3").addClass("current");
    $("#mm-body li.menu-list-mm").addClass("current");
	$("#lab-body li.menu-list-lab").addClass("current");



/**
 * 下拉菜单
 */
    $(".menu ul.menu-list > li").hover(
        function () {
            $(this).find("> a").addClass("hover");
            $(this).find(".menu-sublist").slideDown(100);
        },
        function () {
            $(this).find("> a").removeClass("hover");
            $(this).find(".menu-sublist").slideUp(100);
        }
    );





})

