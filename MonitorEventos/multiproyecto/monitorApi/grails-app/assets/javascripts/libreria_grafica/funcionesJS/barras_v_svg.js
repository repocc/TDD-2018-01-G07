
    new RGraph.SVG.Bar({
        id: 'chart-container1',
        data: [13,56,16,15,12],
        options: {
            colorsSequential: true,
            colors: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
            tooltips: ["Africa", "Asia", "Europe", "Latin America", "North America"],
            xaxisLabels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
            xaxisColor: '#aaa',
            yaxisColor: '#aaa',
            yaxisLabelsCount: 11,
            yaxisUnitsPost: 'm',
            gutterLeft: 45,
            textSize: 9,
            hmargin: 25
        }
    }).draw().on('beforetooltip', function ()
    {
        RGraph.SVG.tooltips.style.backgroundColor = 'black';
        RGraph.SVG.tooltips.style.color           = 'white';
        RGraph.SVG.tooltips.style.fontWeight      = 'bold';
    });
