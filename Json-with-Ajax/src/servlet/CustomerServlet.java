package servlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/pages/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Content-Type","application/json");

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();


            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                int salary = rst.getInt(4);

                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id",id);
                customer.add("name",name);
                customer.add("address",address);
                customer.add("salary",salary);

                allCustomers.add(customer.build());
            }

            resp.getWriter().println(allCustomers.build());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        int cusSalary = Integer.parseInt(req.getParameter("cusSalary"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");

            PreparedStatement pstm = connection.prepareStatement("insert into customer values(?,?,?,?)");
            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);
            pstm.setObject(4, cusSalary);
            if (pstm.executeUpdate() > 0) {
//                        resp.getWriter().println("Customer Added..!");
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder AddedCus = Json.createObjectBuilder();

                resp.addHeader("Content-Type","application/json");

                AddedCus.add("state","OK");
                AddedCus.add("message","Successfully Added");
                AddedCus.add("data","");

                resp.getWriter().println(AddedCus.build());
        }

        } catch (SQLException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorCus = Json.createObjectBuilder();
            resp.addHeader("Content-Type","application/json");
            ErrorCus.add("state","Error");
            ErrorCus.add("message",e.getMessage());
            ErrorCus.add("data","");

//            resp.setStatus(400);

            resp.getWriter().println(ErrorCus.build());

        } catch (ClassNotFoundException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorCus = Json.createObjectBuilder();

            ErrorCus.add("state","Error");
            ErrorCus.add("message",e.getMessage());
            ErrorCus.add("data","");


            resp.getWriter().println(ErrorCus.build());
        }


    }


        @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String cusID = req.getParameter("cusID");
            String cusName = req.getParameter("cusName");
            String cusAddress = req.getParameter("cusAddress");
            int cusSalary = Integer.parseInt(req.getParameter("cusSalary"));

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");

                PreparedStatement pstm = connection.prepareStatement("update customer set name=?,address=? ,telephone=? where customer_id=?");
                pstm.setObject(4, cusID);
                pstm.setObject(1, cusName);
                pstm.setObject(2, cusAddress);
                pstm.setObject(3, cusSalary);
                if (pstm.executeUpdate() > 0) {
//                        resp.getWriter().println("Customer Added..!");
                    resp.addHeader("Content-Type","application/json");
                    JsonObjectBuilder AddedCus = Json.createObjectBuilder();

                    resp.addHeader("Content-Type","application/json");

                    AddedCus.add("state","OK");
                    AddedCus.add("message","Successfully updated");
                    AddedCus.add("data","");

                    resp.getWriter().println(AddedCus.build());
                }

            } catch (SQLException e) {
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder ErrorCus = Json.createObjectBuilder();
                resp.addHeader("Content-Type","application/json");
                ErrorCus.add("state","Error");
                ErrorCus.add("message",e.getMessage());
                ErrorCus.add("data","");

//            resp.setStatus(400);

                resp.getWriter().println(ErrorCus.build());

            } catch (ClassNotFoundException e) {
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder ErrorCus = Json.createObjectBuilder();

                ErrorCus.add("state","Error");
                ErrorCus.add("message",e.getMessage());
                ErrorCus.add("data","");


                resp.getWriter().println(ErrorCus.build());
            }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        int cusSalary = Integer.parseInt(req.getParameter("cusSalary"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jsonajax", "root", "12345678");

            PreparedStatement pstm = connection.prepareStatement("delete from customer where customer_id=?");
            pstm.setObject(1, cusID);
            if (pstm.executeUpdate() > 0) {
//                        resp.getWriter().println("Customer Added..!");
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder AddedCus = Json.createObjectBuilder();

                resp.addHeader("Content-Type","application/json");

                AddedCus.add("state","OK");
                AddedCus.add("message","Successfully Added");
                AddedCus.add("data","");

                resp.getWriter().println(AddedCus.build());
            }

        } catch (SQLException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorCus = Json.createObjectBuilder();
            resp.addHeader("Content-Type","application/json");
            ErrorCus.add("state","Error");
            ErrorCus.add("message",e.getMessage());
            ErrorCus.add("data","");

//            resp.setStatus(400);

            resp.getWriter().println(ErrorCus.build());

        } catch (ClassNotFoundException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder ErrorCus = Json.createObjectBuilder();

            ErrorCus.add("state","Error");
            ErrorCus.add("message",e.getMessage());
            ErrorCus.add("data","");


            resp.getWriter().println(ErrorCus.build());
        }

    }
}
