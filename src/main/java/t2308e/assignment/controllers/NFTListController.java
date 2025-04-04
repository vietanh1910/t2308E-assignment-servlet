package t2308e.assignment.controllers;

import t2308e.assignment.entity.NFT;
import t2308e.assignment.repositories.NFTRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NFTListController extends HttpServlet {
    private final NFTRepository nftRepository = new NFTRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer page = request.getAttribute("page") != null ? (Integer) request.getAttribute("page") : 1;
        Integer size = request.getAttribute("size") != null ? (Integer) request.getAttribute("size") : 10;
        List<NFT> nftList = nftRepository.findAll(page, size);
        request.setAttribute("nfts", nftList);
        request.getRequestDispatcher("/nft/nft-list.jsp").forward(request, response);
    }
}
