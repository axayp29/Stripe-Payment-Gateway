<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Page</title>

    <!-- Bootstrap CSS link -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body class="bg-light">

    <div class="container mt-5">
        <h2 class="text-center mb-4">Product Page</h2>

        <div class="row">
            <!-- Product 1 -->
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/resources/iphone.jpg" height="380px" width="200px" class="card-img-top" alt="Product 1 Logo">
                    <div class="card-body">
                        <h5 class="card-title">Product 1</h5>
                        <p class="card-text">Amount: 120000.00</p>
                        <div class="form-check">
                         <input type="hidden" value="120000" id="product1">
                            <input class="form-check-input product-checkbox" type="checkbox" value="120000" id="product1Checkbox">
                            <label class="form-check-label" for="product1Checkbox">
                                Add to Cart
                            </label>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Product 2 -->
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/resources/product.jpg" height="380px" width="200px" class="card-img-top" alt="Product 2 Logo">
                    <div class="card-body">
                        <h5 class="card-title">Product 2</h5>
                        <p class="card-text">Amount: 500.00</p>
                        <div class="form-check">
                        <input type="hidden" value="500" id="product2">
                            <input class="form-check-input product-checkbox" type="checkbox" value="500" id="product2Checkbox">
                            <label class="form-check-label" for="product2Checkbox">
                                Add to Cart
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            
            
            <!-- Product 2 -->
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${pageContext.request.contextPath}/resources/dairyProduct.jpg" height="380px" width="200px" class="card-img-top" alt="Product 2 Logo">
                    <div class="card-body">
                        <h5 class="card-title">Product 3</h5>
                        <p class="card-text">Amount: 300.00</p>
                        <div class="form-check">
                        <input type="hidden" value="300" id="product3">
                            <input class="form-check-input product-checkbox" type="checkbox" value="300" id="product3Checkbox">
                            <label class="form-check-label" for="product3Checkbox">
                                Add to Cart
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            
            <form action="${pageContext.request.contextPath}/checkout" id="checkoutFrom">
            
            <input type="hidden" id="checkOutAmount" name="checkOutAmount" >
             <button type="submit" id="checkOutbutton" hidden="hidden"></button>
            </form>

            <!-- Add more product cards as needed -->

        </div>

        <!-- Checkout Button -->
        <div class="text-center mt-4">
            <button type="button" class="btn btn-primary" id="checkoutButton">Checkout</button>
        </div>
    </div>

    <!-- Bootstrap JS and Popper.js scripts (optional, for Bootstrap features) -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
    <script>
        $(document).ready(function() {
        	
        	console.log("CALLED");
            var selectedProducts = [];

            // Listen for checkbox changes
            $('.product-checkbox').change(function() {
                var productId = $(this).attr('id');
                console.log("CALLED : "+productId);
                
                // Check if the checkbox is checked
                if ($(this).prop('checked')) {
                    // Add the product ID to the list
                    selectedProducts.push(productId);
                } else {
                	console.log("REMOVING");
                	 var indexToRemove = selectedProducts.indexOf(productId);
                     if (indexToRemove !== -1) {
                         selectedProducts.splice(indexToRemove, 1);
                     }
                }
                
                
                
            });

            // Listen for checkout button click
            $('#checkoutButton').click(function() {
                // Display the selected product IDs in the console (you can modify this part)
                console.log('Selected Products:', selectedProducts);
                
               /*  $.each(selectedProducts, function(index, value) {
                    selectedProducts[index] = value.replace("Checkbox", "");
                });
                 */
                console.log('Selected Products:', selectedProducts);
                
                var sum = 0;
                
                $.each(selectedProducts, function(index, id) {
                    // Get the value from the HTML element with the corresponding ID
                    var value = $("#" + id).val();

                    // Convert the value to a number and add it to the sum
                    sum += parseFloat(value);
                });
                
                console.log(sum)
                
                $('#checkOutAmount').val(sum);
                
                $("#checkOutbutton").click();
                
                
               /*  $.ajax({
                    url: '${pageContext.request.contextPath}/checkout?amount='+sum, // Replace with your API endpoint
                    method: 'GET',
                    dataType: 'application/json', // expected data type from the server
                    success: function (data) {
                
                      console.log('Data received:', data);
                    },
                    error: function (error) {
                      // Handle errors here
                      console.error('Error:', error);
                    }
                  }); */
                
                
            });
        });
    </script>
</body>
</html>
