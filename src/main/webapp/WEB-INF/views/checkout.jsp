<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Simple Checkout Page</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  
  <style>
    body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f4;
    }

    header {
      background-color: #333;
      color: white;
      text-align: center;
      padding: 1em;
    }

    .card-box {
      max-width: 600px;
      margin: 20px auto;
      padding: 20px;
      background-color: #fff;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    img {
      max-width: 100%;
      height: auto;
    }

    .card-input {
      width: 100%;
      padding: 10px;
      margin-bottom: 20px;
      box-sizing: border-box;
    }

    .checkout-btn {
      background-color: #4CAF50;
      color: white;
      padding: 10px;
      border: none;
      width: 100%;
      cursor: pointer;
    }

    .checkout-btn:hover {
      background-color: #45a049;
    }
  </style>
</head>
<body>

<div class="card-box">
  <header>
    <img src="${pageContext.request.contextPath}/resources/green-apex.jpg" alt="Your Logo" class="img-fluid">
  </header>

  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <form id="checkout-form" action="/createPaymentIntent" method="post">
              <!-- Your form fields go here -->
              <div class="form-group">
                <!-- <label for="amount">Total Amount:</label> -->
                <h4>Total Amount: ${amount/100}</h4>
                <input type="hidden" class="form-control" name="totalAmount" id="amount" value="${amount/100}" placeholder="Enter the amount" required> 
              </div>

              <script src="https://checkout.stripe.com/checkout.js"
                class="stripe-button" 
                data-key="pk_test_51Lc4xSSAs5DsDSgQZDwuQXQcocduHOoueQ8TNOAXKLN0nXPfzvzX4gFRsN8ia3VOGaOtqDgUPygzzaOeJQYzy6xK00ZsOBsxOY" 
                data-amount= ${amount}
                data-currency="inr"
                data-name="Akshay-Testing" data-description="2 widgets"
                data-image="${pageContext.request.contextPath}/resources/green-apex.jpg"
                data-locale="auto">
              </script>

              <!-- <button type="button" class="btn btn-primary checkout-btn" id="checkout-button">Checkout</button> -->
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- Stripe.js library -->
<script src="https://js.stripe.com/v3/"></script>

</body>
</html>
