package pl.graniec.coralreef.demo;

import java.util.ArrayList;
import java.util.List;

import pulpcore.Stage;
import pulpcore.animation.Easing;
import pulpcore.image.CoreFont;
import pulpcore.image.CoreGraphics;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;

public class HintSprite extends Sprite {
	
	/** Margin from bottom for hints */
	private static final int BOTTOM_MARGIN = 5;
	/** Hint scroll speed in pixels per second */
	private static final int SCROLL_SPEED = 100;
	
	/** Hints background sprite */
	private Sprite hintsBgSprite;

	/** Displayed hints */
	private List<String> hints = new ArrayList<String>();
	/** Current hint label */
	private Label currentHintLabel;
	/** Index of the next hint */
	private int nextHintIndex = 0;
	
	/** White system font */
	private CoreFont whiteFont;
	
	public HintSprite() {
		super(0, 0, Stage.getWidth(), Stage.getHeight());
		
		whiteFont = CoreFont.getSystemFont().tint(0xFFFFFFFF);
		
		final double hintsBgHeight = (whiteFont.getHeight() + BOTTOM_MARGIN * 2);
		hintsBgSprite = new FilledSprite(0, Stage.getHeight() - hintsBgHeight, Stage.getWidth(), hintsBgHeight, 0xAA000000);
	}
	
	public void addHint(final String hint) {
		hints.add(hint);
	}
	
	@Override
	protected void drawSprite(CoreGraphics g) {

		hintsBgSprite.draw(g);
		
		if (currentHintLabel != null) {
			currentHintLabel.draw(g);
		}
	}
	
	
	@Override
	public void update(int elapsedTime) {
		super.update(elapsedTime);
		
		// suffle hints
		if (hints.size() > 0) {
			
			if (nextHintIndex >= hints.size()) {
				nextHintIndex = 0;
			}
			
			if (currentHintLabel == null) {
				
				currentHintLabel = new Label(
						whiteFont,
						hints.get(nextHintIndex++),
						Stage.getWidth(),
						Stage.getHeight() - BOTTOM_MARGIN
				);
				
				currentHintLabel.anchorX.set(0.0);
				currentHintLabel.anchorY.set(1.0);
				
				final int strWidth = whiteFont.getStringWidth(hints.get(nextHintIndex-1));
				
				currentHintLabel.x.animateTo(
						-strWidth,
						(strWidth + Stage.getWidth()) * 1000 / SCROLL_SPEED
				);
				
				
			} else {
				
				currentHintLabel.update(elapsedTime);
				
				if (!currentHintLabel.x.isAnimating()) {
					currentHintLabel = null;
				}
			}
		}
		
		hintsBgSprite.y.set(y.get() + Stage.getHeight() - hintsBgSprite.height.get());
		
		if (currentHintLabel != null) {
			currentHintLabel.y.set(y.get() + Stage.getHeight() - BOTTOM_MARGIN);
			
			updateDirtyRect();
			getDirtyRect().setBounds((int)currentHintLabel.x.get(), (int)currentHintLabel.y.get(), (int)currentHintLabel.width.get(), (int)currentHintLabel.height.get());
			
		}
		
		
	}
	
	public void hide() {
		y.animateTo(hintsBgSprite.height.get(), 500, Easing.REGULAR_OUT);
	}
	
	public void show() {
		y.animateTo(0, 500, Easing.REGULAR_OUT);
	}

}
