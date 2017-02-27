
public interface BoardInterface {
	
	/**
     * Configures a board with specific set of behaviour;
     * must be implemented by a subclass.
     * 
     * @param frame The JFrame on which the board will be created.
     */
    public abstract void initBoard(JFrame frame);
    
    /**
     * Notifies the board frame on changes in the number of birds/grains;
     * must be implemented by a subclass.
     * 
     */
    public abstract void updateStockDisplay();

}
