package servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = "/pages/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");
            PreparedStatement pstm = connection.prepareStatement("select * from item");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Content-Type","application/json");

            JsonArrayBuilder allItems = Json.createArrayBuilder();


            while (rst.next()) {
                String code = rst.getString(1);
                String description = rst.getString(2);
                String qty = rst.getString(3);
                int unitPrice = rst.getInt(4);

                JsonObjectBuilder item = Json.createObjectBuilder();

                item.add("code",code);
                item.add("description",description);
                item.add("qty",qty);
                item.add("unitPrice",unitPrice);

                allItems.add(item.build());
            }

            resp.getWriter().println(allItems.build());


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("iCode");
        String description = req.getParameter("iName");
        String qty = req.getParameter("iQty");
        int unitPrice = Integer.parseInt(req.getParameter("iPrice"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");

            PreparedStatement pstm = connection.prepareStatement("insert into item values(?,?,?,?)");
            pstm.setObject(1, itemCode);
            pstm.setObject(2, description);
            pstm.setObject(3, qty);
            pstm.setObject(4, unitPrice);
            if (pstm.executeUpdate() > 0) {
//                        resp.getWriter().println("Customer Added..!");
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder AddedItem = Json.createObjectBuilder();

                resp.addHeader("Content-Type","application/json");

                AddedItem.add("state","OK");
                AddedItem.add("message","Successfully Added");
                AddedItem.add("data","");

                resp.getWriter().print(AddedItem.build());

            }

        } catch (SQLException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorItem = Json.createObjectBuilder();
            resp.addHeader("Content-Type","application/json");
            ErrorItem.add("state","Error");
            ErrorItem.add("message",e.getMessage());
            ErrorItem.add("data","");

//            resp.setStatus(400);

            resp.getWriter().print(ErrorItem.build());

        } catch (ClassNotFoundException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorItem = Json.createObjectBuilder();
            resp.addHeader("Content-Type","application/json");
            ErrorItem.add("state","Error");
            ErrorItem.add("message",e.getMessage());
            ErrorItem.add("data","");


            resp.getWriter().print(ErrorItem.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type","application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject itemOB = reader.readObject();

        String itemCode = itemOB.getString("code");
        String description = itemOB.getString("description");
        String qty = itemOB.getString("qty");
        String unitPrice = itemOB.getString("unitPrice");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");

            PreparedStatement pstm = connection.prepareStatement("update item set description=?,quantity=? ,unit_price=? where item_code=?");
            pstm.setObject(4, itemCode);
            pstm.setObject(1, description);
            pstm.setObject(2, qty);
            pstm.setObject(3, unitPrice);
            if (pstm.executeUpdate() > 0) {
//                        resp.getWriter().println("Customer Added..!");
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder AddedItem = Json.createObjectBuilder();

                resp.addHeader("Content-Type","application/json");

                AddedItem.add("state","OK");
                AddedItem.add("message","Successfully Updated");
                AddedItem.add("data","");

                resp.getWriter().print(AddedItem.build());

            }

        } catch (SQLException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorItem = Json.createObjectBuilder();
            resp.addHeader("Content-Type","application/json");
            ErrorItem.add("state","Error");
            ErrorItem.add("message",e.getMessage());
            ErrorItem.add("data","");

//            resp.setStatus(400);

            resp.getWriter().print(ErrorItem.build());

        } catch (ClassNotFoundException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorItem = Json.createObjectBuilder();
            resp.addHeader("Content-Type","application/json");
            ErrorItem.add("state","Error");
            ErrorItem.add("message",e.getMessage());
            ErrorItem.add("data","");


            resp.getWriter().print(ErrorItem.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
