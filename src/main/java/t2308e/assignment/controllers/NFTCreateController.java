package t2308e.assignment.controllers; // Giả sử bạn đặt controller trong package này

import t2308e.assignment.entity.NFT;
import t2308e.assignment.enums.Category;
import t2308e.assignment.enums.Status;
import t2308e.assignment.repositories.NFTRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(name = "NFTCreateController", urlPatterns = "/create-nft")
public class NFTCreateController extends HttpServlet {

    private final NFTRepository nftRepository = new NFTRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/nft/nft-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            // 1. Lấy dữ liệu từ request parameters
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String imageUrl = req.getParameter("imageUrl");
            String priceStr = req.getParameter("price");
            String creator = req.getParameter("creator");
            String categoryStr = req.getParameter("category");
            String ownerWalletAddress = req.getParameter("ownerWalletAddress");
            String statusStr = req.getParameter("status");

            // 2. Validate dữ liệu cơ bản (ví dụ: kiểm tra null/empty)
            if (name == null || name.trim().isEmpty() ||
                    priceStr == null || priceStr.trim().isEmpty() ||
                    categoryStr == null || categoryStr.trim().isEmpty() ||
                    statusStr == null || statusStr.trim().isEmpty() ||
                    creator == null || creator.trim().isEmpty() ||
                    ownerWalletAddress == null || ownerWalletAddress.trim().isEmpty()) {

                // Xử lý lỗi: Thiếu thông tin bắt buộc
                req.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin bắt buộc.");
                // Gửi lại các giá trị đã nhập để người dùng không phải nhập lại
                req.setAttribute("submittedName", name);
                req.setAttribute("submittedDescription", description);
                req.setAttribute("submittedImageUrl", imageUrl);
                req.setAttribute("submittedPrice", priceStr);
                req.setAttribute("submittedCreator", creator);
                req.setAttribute("submittedCategory", categoryStr);
                req.setAttribute("submittedOwnerWalletAddress", ownerWalletAddress);
                req.setAttribute("submittedStatus", statusStr);

                req.getRequestDispatcher("/nft/nft-create.jsp").forward(req, resp);
                return; // Kết thúc xử lý
            }

            // 3. Chuyển đổi kiểu dữ liệu và xử lý Enum
            BigDecimal price = null;
            try {
                price = new BigDecimal(priceStr);
                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    throw new NumberFormatException("Giá không được âm.");
                }
            } catch (NumberFormatException e) {
                // Xử lý lỗi: Giá không hợp lệ
                req.setAttribute("errorMessage", "Giá tiền không hợp lệ: " + e.getMessage());
                // Gửi lại các giá trị đã nhập
                req.setAttribute("submittedName", name);
                req.setAttribute("submittedDescription", description);
                req.setAttribute("submittedImageUrl", imageUrl);
                // Không gửi lại giá không hợp lệ
                req.setAttribute("submittedCreator", creator);
                req.setAttribute("submittedCategory", categoryStr);
                req.setAttribute("submittedOwnerWalletAddress", ownerWalletAddress);
                req.setAttribute("submittedStatus", statusStr);
                req.getRequestDispatcher("/nft/nft-create.jsp").forward(req, resp);
                return;
            }

            Category category = null;
            try {
                // Chuyển đổi thành chữ hoa để khớp với tên Enum (thường là convention)
                category = Category.valueOf(categoryStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Xử lý lỗi: Category không hợp lệ
                req.setAttribute("errorMessage", "Loại NFT không hợp lệ.");
                // Gửi lại các giá trị đã nhập
                req.setAttribute("submittedName", name);
                req.setAttribute("submittedDescription", description);
                req.setAttribute("submittedImageUrl", imageUrl);
                req.setAttribute("submittedPrice", priceStr);
                req.setAttribute("submittedCreator", creator);
                // Không gửi lại category không hợp lệ
                req.setAttribute("submittedOwnerWalletAddress", ownerWalletAddress);
                req.setAttribute("submittedStatus", statusStr);
                req.getRequestDispatcher("/nft/nft-create.jsp").forward(req, resp);
                return;
            }

            Status status = null;
            try {
                status = Status.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Xử lý lỗi: Status không hợp lệ
                req.setAttribute("errorMessage", "Trạng thái NFT không hợp lệ.");
                // Gửi lại các giá trị đã nhập
                req.setAttribute("submittedName", name);
                req.setAttribute("submittedDescription", description);
                req.setAttribute("submittedImageUrl", imageUrl);
                req.setAttribute("submittedPrice", priceStr);
                req.setAttribute("submittedCreator", creator);
                req.setAttribute("submittedCategory", categoryStr);
                req.setAttribute("submittedOwnerWalletAddress", ownerWalletAddress);
                // Không gửi lại status không hợp lệ
                req.getRequestDispatcher("/nft/nft-create.jsp").forward(req, resp);
                return;
            }

            Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
            NFT newNft = new NFT();
            newNft.setName(name.trim());
            newNft.setDescription(description != null ? description.trim() : null); // Cho phép description null
            newNft.setImageUrl(imageUrl != null ? imageUrl.trim() : null); // Cho phép imageUrl null
            newNft.setPrice(price);
            newNft.setCreatedAt(createdAt);
            newNft.setCreator(creator.trim());
            newNft.setCategory(category);
            newNft.setOwnerWalletAddress(ownerWalletAddress.trim());
            newNft.setStatus(status);

            nftRepository.save(newNft);
            resp.sendRedirect(req.getContextPath() + "/nft-list");

        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi ra console server
            req.setAttribute("errorMessage", "Đã có lỗi hệ thống xảy ra: " + e.getMessage());
            req.getRequestDispatcher("/nft/nft-create.jsp").forward(req, resp);
        }
    }
}