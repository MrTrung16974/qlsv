<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>QLSV</title>
<meta content="" name="description">
<meta content="" name="keywords">
<link href="/view/assets/img/favicon.png" rel="icon">
<link href="/view/assets/img/apple-touch-icon.png"
	rel="apple-touch-icon">
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
<link href="/view/assets/img/favicon.png" rel="icon">
<link href="/view/assets/img/apple-touch-icon.png"
	rel="apple-touch-icon">
<link href="https://fonts.gstatic.com" rel="preconnect">
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	rel="stylesheet">
<link href="/view/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/view/assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="/view/assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="/view/assets/vendor/quill/quill.snow.css" rel="stylesheet">
<link href="/view/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
<link href="/view/assets/vendor/remixicon/remixicon.css"
	rel="stylesheet">
<link href="/view/assets/vendor/simple-datatables/style.css"
	rel="stylesheet">
<style>
    .logo {
        display: flex;
        align-items: center;
        text-decoration: none;
        color: #333;
        font-size: 1.5rem;
        font-weight: 700;
        transition: transform 0.3s ease;
    }

    .logo:hover {
        transform: scale(1.1); /* Thêm hiệu ứng hover */
    }

    .logo-img {
        width: 40px; /* Kích thước logo nếu bạn dùng hình ảnh */
        height: 40px;
        object-fit: cover;
    }

    .logo-text {
        font-family: 'Arial', sans-serif;
        font-weight: bold;
        color: #2d2d2d;
    }

    .logo:hover .logo-text {
        color: #007bff; /* Thay đổi màu chữ khi hover */
    }

    .d-flex {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .py-4 {
        padding-top: 1.5rem;
        padding-bottom: 1.5rem;
    }
</style>
</head>
<body>
	<main>
		<div class="container">

			<section
				class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
				<div class="container">
					<div class="row justify-content-center">
						<div
							class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">

							<!-- End Logo -->

							<div class="card mb-3">

								<div class="card-body">

									<div class="pt-4 pb-2">
										<h5 class="card-title text-center pb-0 fs-4">Đăng nhập</h5>
									</div>

									<form action="${pageContext.request.contextPath}/login"
										method="post" class="row g-3 needs-validation" novalidate>

										<div class="col-12">
											<label for="yourUsername" class="form-label">Tài
												khoản</label>
											<div class="input-group has-validation">
												<input type="text" name="username" class="form-control"
													placeholder="Nhập tài khoản của bạn" required>
											</div>
										</div>

										<div class="col-12">
											<label for="yourPassword" class="form-label">Mật khẩu</label>
											<input type="password" name="password" class="form-control"
												placeholder="Nhập mật khẩu của bạn" required>
										</div>

										<div class="col-12">
											<div class="form-check">
												<input class="form-check-input" type="checkbox"
													name="remember" value="true" id="rememberMe"> <label
													class="form-check-label" for="rememberMe">Ghi nhớ
													mật khẩu?</label>
											</div>
											<c:if test="${not empty error}">
												<p style="color: red;">${error}</p>
											</c:if>
										</div>
										<div class="col-12">
											<button class="btn btn-primary w-100" type="submit">Đăng
												nhập</button>
										</div>
										<div class="col-12">
											<p class="small mb-0">
												Bạn quên mật khẩu?
											</p>
										</div>
									</form>
								</div>
							</div>

						</div>
					</div>
				</div>

			</section>

		</div>
	</main>
</body>

</html>