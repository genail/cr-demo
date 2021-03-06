package pl.graniec.coralreef.demo;

import pl.graniec.coralreef.pulpcore.desktop.CoreApplication;
import pulpcore.Input;
import pulpcore.image.CoreGraphics;
import pulpcore.scene.Scene;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Sprite;

public class DemoScene extends Scene {

	/** Black background sprite */
	private Sprite bgSprite;
	/** Hint sprite */
	private HintSprite hintSprite;

	/** Hints visible */
	private boolean hintsVisible = true;
	
	
	@Override
	public void load() {
		super.load();
		
		
		bgSprite = new FilledSprite(0xFF999999);
		hintSprite = new HintSprite();
		
		hintSprite.addHint("Hello, I am the hint panel! Press the H button to hide or unhide me.");
		
	}
	
	@Override
	public void drawScene(CoreGraphics g) {
		bgSprite.draw(g);
		hintSprite.draw(g);
	}

	@Override
	public void updateScene(int elapsedTime) {
		
		hintSprite.update(elapsedTime);
		
		// hide or unhide hint panel
		if (Input.isPressed(Input.KEY_H)) {
			if (hintsVisible) {
				hintSprite.hide();
				hintsVisible = false;
			} else {
				hintSprite.show();
				hintsVisible = true;
			}
		}
		
	}
	
	public static void main(String[] args) {
		final CoreApplication app = new CoreApplication(DemoScene.class);
		app.run();
	}

}
