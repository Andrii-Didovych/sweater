<#include "security.ftl">
<#macro page tolbar>
<!DOCTYPE html>
<html lang="en" style="min-height: 100%; position: relative;">
<head>
    <meta charset="UTF-8"/>
    <title>Sweater</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous"/>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body style="background-color: #e6ecf0; min-width: 870px;">
<#if tolbar>
    <#include "navbar.ftl">
</#if>
    <#nested>
<#if isActive>
<div style="height: 60px;"></div>
<div class="justify-content-center row">
<div style="position: absolute; bottom: 15px; margin: auto;">
        <a target="_blank" href="https://www.linkedin.com/in/andrii-didovych/"><img src="http://www.aphi.nz/img/linkedin.png" style="height: 40px; border-radius: 4px; border: 1px solid white;"/></a>
        <a target="_blank" href="https://www.facebook.com/profile.php?id=100008114774126"><img src="http://www.jmkxyy.com/find-me-on-facebook-icon/find-me-on-facebook-icon-11.jpg" style="height: 40px; border-radius: 4px; border: 1px solid white; "/></a>
        <a target="_blank" href="https://github.com/Andrii-Didovych/sweater"><img src="https://www.modmy.com/sites/modmy.com/files/styles/large/public/article-images/2017/08/github-app-button.png?itok=ONaR9O8z" style="height: 42px;"/></a>
</div>
</div>
</#if>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>
</#macro>