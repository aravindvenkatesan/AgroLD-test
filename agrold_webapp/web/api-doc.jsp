<%-- 
    Document   : index.jsp
    Created on : Jul 15, 2015, 4:29:18 PM
    Author     : tagny
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>         
        <!--link rel="icon" type="image/png" href="swagger/images/favicon-32x32.png" sizes="32x32" />
        <link rel="icon" type="image/png" href="swagger/images/favicon-16x16.png" sizes="16x16" /-->
        <link href='swagger/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
        <link href='swagger/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
        <link href='swagger/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
        <link href='swagger/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
        <link href='swagger/css/print.css' media='print' rel='stylesheet' type='text/css'/>        
        <script src='swagger/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
        <script src='swagger/lib/jquery.slideto.min.js' type='text/javascript'></script>
        <script src='swagger/lib/jquery.wiggle.min.js' type='text/javascript'></script>
        <script src='swagger/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
        <script src='swagger/lib/handlebars-2.0.0.js' type='text/javascript'></script>
        <script src='swagger/lib/underscore-min.js' type='text/javascript'></script>
        <script src='swagger/lib/backbone-min.js' type='text/javascript'></script>
        <script src='swagger/swagger-ui.js' type='text/javascript'></script>
        <script src='swagger/lib/highlight.7.3.pack.js' type='text/javascript'></script>
        <script src='swagger/lib/marked.js' type='text/javascript'></script>
        <script src='swagger/lib/swagger-oauth.js' type='text/javascript'></script>        
        
        <script type="text/javascript">
            $(function () {
                var url = window.location.search.match(/url=([^&]+)/);
                if (url && url.length > 1) {
                    url = decodeURIComponent(url[1]);
                } else {
                    url = "../swagger/agrold.json";
                }
                window.swaggerUi = new SwaggerUi({
                    url: url,
                    dom_id: "swagger-ui-container",
                    supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
                    onComplete: function (swaggerApi, swaggerUi) {
                        if (typeof initOAuth == "function") {
                            initOAuth({
                                clientId: "your-client-id",
                                realm: "your-realms",
                                appName: "your-app-name"
                            });
                        }
                        $('pre code').each(function (i, e) {
                            hljs.highlightBlock(e)
                        });
                        addApiKeyAuthorization();
                    },
                    onFailure: function (data) {
                        log("Unable to Load SwaggerUI");
                    },
                    docExpansion: "none",
                    apisSorter: "alpha"
                });
                function addApiKeyAuthorization() {
                    var key = encodeURIComponent($('#input_apiKey')[0].value);
                    if (key && key.trim() != "") {
                        var apiKeyAuth = new SwaggerClient.ApiKeyAuthorization("api_key", key, "query");
                        window.swaggerUi.api.clientAuthorizations.add("api_key", apiKeyAuth);
                        log("added key " + key);
                    }
                }
                $('#input_apiKey').change(addApiKeyAuthorization);
                // if you have an apiKey you would like to pre-populate on the page for demonstration purposes...
                /*
                 var apiKey = "myApiKeyXXXX123456789";
                 $('#input_apiKey').val(apiKey);
                 */
                window.swaggerUi.load();
                function log() {
                    if ('console' in window) {
                        console.log.apply(console, arguments);
                    }
                }
            });
        </script>
        
        <title> AgroLD : API documentation </title> 
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="styles/style1.css" rel="stylesheet" type="text/css"/>
        <link href="styles/menu1.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="swagger-section">
        <div id="wrapper"> 
            <jsp:include page="header.html"></jsp:include>
                <section>
                    <div id='header' style="display: none">
                        <div class="swagger-ui-wrap">
                            <a id="logo" href="http://swagger.io">swagger</a>
                            <form id='api_selector'>
                                <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
                                <div class='input'><input placeholder="api_key" id="input_apiKey" name="apiKey" type="text"/></div>
                                <div class='input'><a id="explore" href="#">Explore</a></div>
                            </form>
                        </div>
                    </div>

                    <div id="message-bar" class="swagger-ui-wrap"  style="display: none">&nbsp;</div>
                    <div id="swagger-ui-container" class="swagger-ui-wrap"></div>
                </section><br>
            <jsp:include page="footer.html"></jsp:include>
        </div>
    </body>
</html>