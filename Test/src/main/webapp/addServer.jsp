<%@ page contentType="text/html; charset=UTF-8" %>  
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="/test_app/add" method="post">
        Name :<input type="text"  name="name">
        Number :<input type="number"  name="number">
        <input type="submit" value="Add">
    </form>
</body>
</html>
