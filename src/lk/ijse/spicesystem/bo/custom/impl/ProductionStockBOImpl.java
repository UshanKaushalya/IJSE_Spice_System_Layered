package lk.ijse.spicesystem.bo.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.spicesystem.bo.custom.ProductionStockBO;
import lk.ijse.spicesystem.dao.DAOFactory;
import lk.ijse.spicesystem.dao.custom.ProductionStockDAO;
import lk.ijse.spicesystem.dto.ProductionStockDTO;
import lk.ijse.spicesystem.entity.ProductionStock;

import java.sql.SQLException;

public class ProductionStockBOImpl implements ProductionStockBO {

    ProductionStockDAO productionStockDAO = (ProductionStockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCTIONSTOCK);

    @Override
    public String nextId() throws SQLException, ClassNotFoundException {
        return productionStockDAO.nextId();
    }

    @Override
    public boolean add(ProductionStockDTO productionStockDTO) throws SQLException, ClassNotFoundException {
        return productionStockDAO.add(new ProductionStock(productionStockDTO.getProductionStockID(), productionStockDTO.getAmount(), productionStockDTO.getBatchID(), productionStockDTO.getProductionID()));
    }

    @Override
    public ObservableList getProductionStockId(String productionId) throws SQLException, ClassNotFoundException {
        return productionStockDAO.getProductionStockId(productionId);
    }

    @Override
    public int getQtyOnHand(String productionStockId) throws SQLException, ClassNotFoundException {
        return productionStockDAO.getQtyOnHand(productionStockId);
    }

    @Override
    public boolean updateProductionStockTable(String productionStockId, int amount) throws SQLException, ClassNotFoundException {
        return productionStockDAO.updateProductionStockTable(productionStockId, amount);
    }
}
