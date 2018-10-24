package CFour;

public class Player implements Comparable <Player> {

	private String playerName; 
	private String playerSymbol;
	private int numWins, numLosses, numGames;
	
	public Player (){
		playerName = "Player";
		playerSymbol = "*";
		numGames = 0;
		numWins = 0;
		numLosses = 0;
		
	}
	
	public Player (String name, String symbol){
		this();
		playerName = name;
		playerSymbol = symbol;
	}
	public void addNumWins(){
		numWins ++;
		numGames ++;
	}
	public void addLosses(){
		numLosses ++;
		numGames ++;
	}
	public void addDraw(){
		numGames ++;
	}
	public int getNumWins(){
		return numWins;
	}
	public int getNumLosses(){
		return numLosses;
	}
	public int getDraw(){
		return numGames;
	}
	
	public String getSymbol(){
		return playerSymbol;
	}
	public String getName(){
		return playerName;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player otherPlayer = (Player) o;
			if(this.playerName.equalsIgnoreCase(otherPlayer.playerName)){
				if(this.playerSymbol.equalsIgnoreCase(otherPlayer.playerSymbol)){
					if(this.numGames == otherPlayer.numGames){
						if(this.numWins == otherPlayer.numWins){
							if(this.numLosses == otherPlayer.numLosses){
								return true;
							}
						}
					}
				}
			}
		}
		return false; 
	}
	
	@Override
	public String toString(){
		String s = "Player [name = " + playerName +", symbol = " + playerSymbol + ", Wins = " + numWins + ", losses = " + numLosses 
				+ ", Games Played" + numGames + " ]";
		return s;
	}
	
	@Override
	public int compareTo(Player otherP){
		if(this.numWins > otherP.numWins){
			return 1;
		} else if(this.numWins < otherP.numWins){
			return -1;
		} else {
			return 0;
		}
	}

	
	

}
