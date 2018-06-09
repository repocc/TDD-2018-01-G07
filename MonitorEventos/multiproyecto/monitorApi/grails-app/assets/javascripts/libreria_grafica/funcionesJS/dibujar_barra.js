window.onload = function() {

var dataPoints = [];

var chart = new CanvasJS.Chart("barras", {
	animationEnabled: true,
	theme: "Tickets",
	title: {
		text: "Contador Total"
	},
	axisY: {
		title: "Unidades",
		titleFontSize: 24
	},
	data: [{
		type: "column",
		
		dataPoints: dataPoints
	}]
});

function addData(data) {
	for (var i = 0; i < data.length; i++) {
		dataPoints.push({
			x: 10,
			y: data[i].value
		});
	}
	chart.render();

}

$.getJSON("https://localhost:8080/ticket/enviar", addData);

}
