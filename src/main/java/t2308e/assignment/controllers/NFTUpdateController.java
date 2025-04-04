package t2308e.assignment.controllers;

import t2308e.assignment.entity.NFT;
import t2308e.assignment.enums.Category;
import t2308e.assignment.enums.Status;
import t2308e.assignment.repositories.NFTRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class NFTUpdateController extends HttpServlet {
    private final NFTRepository nftRepository = new NFTRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        NFT nft = nftRepository.findById(id);

        if (nft != null) {
            req.setAttribute("nft", nft);
            req.getRequestDispatcher("/nft/nft-update.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "NFT not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String imageUrl = req.getParameter("image_url");
        String priceStr = req.getParameter("price");
        String creator = req.getParameter("creator");
        String categoryStr = req.getParameter("category");
        String ownerWalletAddress = req.getParameter("owner_wallet_address");
        String statusStr = req.getParameter("status");

        Category category = null;
        Status status = null;

        try {
            category = Category.fromValue(Integer.parseInt(categoryStr));
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Danh mục không hợp lệ.");
            req.getRequestDispatcher("/nft/nft-update.jsp").forward(req, resp);
            return;
        }

        try {
            status = Status.fromValue(Integer.parseInt(statusStr));
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Trạng thái không hợp lệ.");
            req.getRequestDispatcher("/nft/nft-update.jsp").forward(req, resp);
            return;
        }

        NFT nft = new NFT(id, name, description, imageUrl, new BigDecimal(priceStr),
                new Timestamp(System.currentTimeMillis()), creator, category, ownerWalletAddress, status);

        nftRepository.update(nft);

        resp.sendRedirect("/nft-list");
    }
}
