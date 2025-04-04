<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="t2308e.assignment.enums.Category, t2308e.assignment.enums.Status, t2308e.assignment.entity.NFT" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Cập nhật NFT</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <h2 class="mt-3">Cập nhật NFT</h2>

  <!-- Hiển thị thông báo lỗi nếu có -->
  <c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
  </c:if>

  <form action="nft-update" method="POST">
    <!-- Thêm ID của NFT vào form để gửi cùng với các dữ liệu khác -->
    <input type="hidden" name="id" value="${nft.id}">

    <div class="mb-3">
      <label for="name" class="form-label">Tên NFT</label>
      <input type="text" class="form-control" id="name" name="name" value="${nft.name}" required>
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Mô tả</label>
      <textarea class="form-control" id="description" name="description" required>${nft.description}</textarea>
    </div>

    <div class="mb-3">
      <label for="image_url" class="form-label">URL Ảnh</label>
      <input type="text" class="form-control" id="image_url" name="image_url" value="${nft.imageUrl}" required>
    </div>

    <div class="mb-3">
      <label for="price" class="form-label">Giá (ETH)</label>
      <input type="number" step="0.01" class="form-control" id="price" name="price" value="${nft.price}" required>
    </div>

    <div class="mb-3">
      <label for="creator" class="form-label">Người tạo</label>
      <input type="text" class="form-control" id="creator" name="creator" value="${nft.creator}" required>
    </div>

    <div class="mb-3">
      <label for="category" class="form-label">Danh mục</label>
      <select class="form-control" id="category" name="category">
        <c:forEach var="category" items="${Category.values()}">
          <option value="${category.value}" ${category.value == nft.category.value ? 'selected' : ''}>${category.description}</option>
        </c:forEach>
      </select>
    </div>

    <div class="mb-3">
      <label for="status" class="form-label">Trạng thái</label>
      <select class="form-control" id="status" name="status">
        <c:forEach var="status" items="${Status.values()}">
          <option value="${status.value}" ${status.value == nft.status.value ? 'selected' : ''}>${status.description}</option>
        </c:forEach>
      </select>
    </div>

    <button type="submit" class="btn btn-success">Cập nhật NFT</button>
  </form>
</div>
</body>
</html>
