<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="t2308e.assignment.entity.NFT" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách NFT</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2 class="mt-3">Danh sách NFT đang bán</h2>
    <a href="/nft-create" class="btn btn-primary mb-3">Thêm NFT</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Ảnh</th>
            <th>Tên</th>
            <th>Giá</th>
            <th>Tác giả</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<NFT> nftList = (List<NFT>) request.getAttribute("nfts");
            if (nftList != null) {
                for (NFT nft : nftList) {
        %>
        <tr>
            <td><img src="<%= nft.getImageUrl() %>" alt="NFT" width="80"></td>
            <td><%= nft.getName() %></td>
            <td><%= nft.getPrice() %> ETH</td>
            <td><%= nft.getCreator() %></td>
            <td>
                <a href="nft-edit?id=<%= nft.getId() %>" class="btn btn-warning btn-sm">Sửa</a>
                <a href="nft-delete?id=<%= nft.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5" class="text-center">Không có NFT nào.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
