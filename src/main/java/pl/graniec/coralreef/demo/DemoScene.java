package pl.graniec.coralreef.demo;

import java.util.ArrayList;
import java.util.List;

import pl.graniec.coralreef.pulpcore.desktop.CoreApplication;
import pulpcore.Input;
import pulpcore.Stage;
import pulpcore.animation.Easing;
import pulpcore.image.CoreFont;
import pulpcore.image.CoreGraphics;
import pulpcore.scene.Scene;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;
import pulpcore.sprite.Sprite;

public class DemoScene extends Scene {

	/** Margin from bottom for hints */
	private static final int BOTTOM_MARGIN = 5;
	/** Hint scroll speed in pixels per second */
	private static final int SCROLL_SPEED = 60;
	
	/** Hint group */
	private Group hintGroup;
	
	/** Black background sprite */
	private Sprite bgSprite;
	/** Hints background sprite */
	private Sprite hintsBgSprite;

	/** Hints visible */
	private boolean hintsVisible = true;
	/** Displayed hints */
	private List<String> hints = new ArrayList<String>();
	/** Current hint label */
	private Label currentHintLabel;
	/** Index of the next hint */
	private int nextHintIndex = 0;
	
	Label lb;
	
	
	/** White system font */
	private CoreFont whiteFont;
	
	public void addHint(final String hint) {
		hints.add(hint);
	}
	
	@Override
	public void load() {
		super.load();
		
		hintGroup = new Group();
		
		whiteFont = CoreFont.getSystemFont().tint(0xFFFFFFFF);
		
		bgSprite = new FilledSprite(0xFF999999);
		
		final double hintsBgHeight = (whiteFont.getHeight() + BOTTOM_MARGIN * 2);
		hintsBgSprite = new FilledSprite(0, Stage.getHeight() - hintsBgHeight, Stage.getWidth(), hintsBgHeight, 0xAA000000);
		
		hintGroup.add(hintsBgSprite);
		
		lb = new Label("aa", 100, 100);
		hintGroup.add(lb);
		
		addHint("Hello, I am hint panel! Press the H button to hide or unhide me.");
	}
	
	@Override
	public void drawScene(CoreGraphics g) {
		bgSprite.draw(g);
//		hintsBgSprite.draw(g);
//		
//		if (currentHintLabel != null) {
//			currentHintLabel.draw(g);
//		}
		hintGroup.draw(g);
	}

	@Override
	public void updateScene(int elapsedTime) {
		
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
				
				hintGroup.add(currentHintLabel);
				
			} else {
				
				currentHintLabel.update(elapsedTime);
				
				if (!currentHintLabel.x.isAnimating()) {
					hintGroup.remove(currentHintLabel);
					currentHintLabel = null;
				}
			}
		}
		
		// hide or unhide hint panel
		if (Input.isPressed(Input.KEY_H)) {
			if (hintsVisible) {
				hintGroup.y.animateTo(hintsBgSprite.height.get(), 500, Easing.REGULAR_OUT);
				hintsVisible = false;
			} else {
				hintGroup.y.animateTo(0, 500, Easing.REGULAR_OUT);
				hintsVisible = true;
			}
		}
		
		hintsBgSprite.update(elapsedTime);
		lb.update(elapsedTime);
		hintGroup.update(elapsedTime);
	}
	
	public static void main(String[] args) {
		final CoreApplication app = new CoreApplication(DemoScene.class);
		app.run();
	}

}
