<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Successful</title>

    <!-- Bootstrap CSS link -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <div class="jumbotron text-center">
            <!-- Success logo -->
            <img src="${pageContext.request.contextPath}/resources/Purchase_Success.png" alt="Success Logo" class="img-fluid mb-3" style="max-width: 150px;">

            <h1 class="display-4">Payment Successful</h1>
            <h4> Amount : ${amount }</h4>
            <h4> Transaction ID : ${paymentIntentId }</h4>
            <p class="lead">Your payment has been processed successfully. Thank you for your purchase!</p>
        </div>
    </div>

    <!-- Bootstrap JS and Popper.js scripts (optional, for Bootstrap features) -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
