package t2308e.assignment.controllers;

import t2308e.assignment.repositories.NFTRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NFTDeleteController extends HttpServlet {
    private final NFTRepository nftRepository = new NFTRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        nftRepository.deleteById(id);

        resp.sendRedirect("/nft-list");
    }
}
