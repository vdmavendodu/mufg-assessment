package com.mufg.assesment.payload;

import java.util.ArrayList;
import java.util.List;

import com.mufg.assesment.dto.Move;
import com.mufg.assesment.dto.Position;

public class Input {
	private Position position;
	private List<Move> move = new ArrayList<Move>();
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public List<Move> getMove() {
		return move;
	}
	public void setMove(List<Move> move) {
		this.move = move;
	}
}
