$(document).ready(function(){
	$(".button-group-down").children().hide();
});
	
	
$(".up-trigger").click(function() {
	$(this).parents(".button-group-up").children().hide();
	$(this).parents(".button-group").children(".button-group-down").children().show();
	
	var editables = $(this).parents("tr").children(".editable");
	
	
		
	for (var i=0; i<editables.length; i++){
		var $editable = $(editables[i]);
		$editable.replaceWith("<td><input name=" + $editable.attr("name") + " value=" + $editable.text() + "></td>");
	}
	
});
			
$(".down-trigger").click(function() {
	$(this).parents(".button-group").children(".button-group-down").children().show();
	$(this).parents(".button-group-up").children().hide();
});
//<div contentEditable="true" ng-model="content">