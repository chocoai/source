(function($) {
    var opt;

    $.fn.jqprint = function (options) {
        opt = $.extend({}, $.fn.jqprint.defaults, options);

        var $element = (this instanceof jQuery) ? this : $(this);
        
        if (opt.operaSupport && $.browser.opera) 
        { 
            var tab = window.open("","jqPrint-preview");
            tab.document.open();

            var doc = tab.document;
        }
        else 
        {
            var $iframe = $("<iframe  />");
        
            if (!opt.debug) { $iframe.css({ position: "absolute", width: "0px", height: "0px", left: "-600px", top: "-600px" }); }

            $iframe.appendTo("body");
            var doc = $iframe[0].contentWindow.document;
        }
        

        doc.write("<title></title><style>@page{size: auto;margin: 0mm;}html{padding:20mm;background-color: #fff;margin:0}body{margin: 10mm 15mm 10mm 15mm;}</style >");
        doc.write('<style>');
        doc.write('table{margin: 0px;border-collapse: collapse;width:100%;overflow:hidden;border: 1px solid #ccc;margin-left:-10mm}');
        doc.write('th{padding-top:5px;padding-bottom:5px;text-overflow:ellipsis;word-break:keep-all;overflow:hidden;font-weight:bold;padding-left:5px;padding-right:5px;font-size: 12px;}');
        doc.write('td{border-bottom: 1px solid #ccc;border-right: 1px solid #ccc;height:25px;line-height:25px;word-break: break-all;padding-left:5px;padding-right:5px;font-size: 12px;}');
        doc.write('</style>');

        if (opt.importCSS)
        {
            if ($("link[media=print]").length > 0) 
            {
                $("link[media=print]").each( function() {
                    doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' media='print' />");
                });
            }
            else 
            {
                $("link").each( function() {
                    doc.write("<link type='text/css' rel='stylesheet' href='" + $(this).attr("href") + "' />");
                });
            }
        }
        
        if (opt.printContainer) { doc.write($element.outer()); }
        else { $element.each( function() { doc.write($(this).html()); }); }
        
        doc.close();
        
        (opt.operaSupport && $.browser.opera ? tab : $iframe[0].contentWindow).focus();
        setTimeout( function() { (opt.operaSupport && $.browser.opera ? tab : $iframe[0].contentWindow).print(); if (tab) { tab.close(); } }, 1000);
    }
    
    $.fn.jqprint.defaults = {
        debug: false,
        importCSS: true, 
        printContainer: true,
        operaSupport: true
    };

    jQuery.fn.outer = function() {
    return $($('<div></div>').html(this.clone())).html();
    } 
})(jQuery);