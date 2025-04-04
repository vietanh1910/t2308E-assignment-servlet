package t2308e.assignment.repositories;

import t2308e.assignment.entity.NFT;
import t2308e.assignment.enums.Category;
import t2308e.assignment.enums.Status;
import t2308e.assignment.helper.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NFTRepository {


    // 1. Thêm NFT vào database
    public void save(NFT nft) {
        String sql = "INSERT INTO nft (name, description, image_url, price, created_at, creator, category, owner_wallet_address, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nft.getName());
            stmt.setString(2, nft.getDescription());
            stmt.setString(3, nft.getImageUrl());
            stmt.setBigDecimal(4, nft.getPrice());
            stmt.setTimestamp(5, nft.getCreatedAt());
            stmt.setString(6, nft.getCreator());
            stmt.setInt(7, nft.getCategory().getValue()); // Lưu category dưới dạng số
            stmt.setString(8, nft.getOwnerWalletAddress());
            stmt.setInt(9, nft.getStatus().getValue()); // Lưu status dưới dạng số
            stmt.executeUpdate();

            // Nếu bạn muốn lấy ID tự động tạo
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                nft.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Lấy tất cả NFT với phân trang
    public List<NFT> findAll(int page, int limit) {
        List<NFT> nftList = new ArrayList<>();
        String sql = "SELECT * FROM nft WHERE status = ? LIMIT ? OFFSET ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, Status.FOR_SALE.getValue()); // Chỉ lấy các NFT đang bán
            stmt.setInt(2, limit); // Tính toán offset
            stmt.setInt(3, (page - 1) * limit); // Số lượng mỗi trang

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nftList.add(mapResultSetToNFT(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nftList;
    }

    // 3. Lấy NFT theo ID
    public NFT findById(int id) {
        String sql = "SELECT * FROM nft WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNFT(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 4. Cập nhật thông tin NFT
    public void update(NFT nft) {
        String sql = "UPDATE nft SET name=?, description=?, image_url=?, price=?, creator=?, category=?, owner_wallet_address=?, status=? WHERE id=?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nft.getName());
            stmt.setString(2, nft.getDescription());
            stmt.setString(3, nft.getImageUrl());
            stmt.setBigDecimal(4, nft.getPrice());
            stmt.setString(5, nft.getCreator());
            stmt.setInt(6, nft.getCategory().getValue()); // Chuyển category thành số
            stmt.setString(7, nft.getOwnerWalletAddress());
            stmt.setInt(8, nft.getStatus().getValue()); // Chuyển status thành số
            stmt.setInt(9, nft.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Xóa NFT theo ID
    public void deleteById(int id) {
        String sql = "DELETE FROM nft WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private NFT mapResultSetToNFT(ResultSet rs) throws SQLException {
        return new NFT(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("image_url"),
                rs.getBigDecimal("price"),
                rs.getTimestamp("created_at"),
                rs.getString("creator"),
                Category.fromValue(rs.getInt("category")),
                rs.getString("owner_wallet_address"),
                Status.fromValue(rs.getInt("status"))
        );
    }
}
