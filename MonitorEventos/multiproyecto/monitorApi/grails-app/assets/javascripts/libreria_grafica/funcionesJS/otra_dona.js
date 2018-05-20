var contador = 0;
function createChart(selector, data) {
    var ctx = jQuery(selector).get(0).getContext("2d");                  
    new Chart(ctx).Doughnut(data);
}
 
function displayChart(urldata, selector) {
    jQuery.get(urldata, function(data) {
        var invoice_status_data = [
                {
                    value: data.status_temp,
                    color: "#26292C",
                    highlight: "#363B3F",
                    label: "Dato #" + contador++
                }
            ];
            createChart(selector, invoice_status_data);
        }
    )
}
 
function displayChart1(urldata, selector) {
    jQuery.get(urldata, function(data) {
            createChart(selector, data);
        }
    )
}
