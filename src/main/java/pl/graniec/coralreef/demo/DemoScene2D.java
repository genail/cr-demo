package pl.graniec.coralreef.demo;

import pl.graniec.coralreef.pulpcore.desktop.CoreApplication;
import pulpcore.Input;
import pulpcore.scene.Scene2D;
import pulpcore.sprite.FilledSprite;

public class DemoScene2D extends Scene2D {
	/** Hint sprite */
	private HintSprite hintSprite;

	/** Hints visible */
	private boolean hintsVisible = true;
	
	
	@Override
	public void load() {
		super.load();
		
		
		add(new FilledSprite(0xFF999999));
		hintSprite = new HintSprite();
		add(hintSprite);
		
		hintSprite.addHint("Hello, I am the hint panel! Press the H button to hide or unhide me.");
		
	}

	@Override
	public void update(int elapsedTime) {
		
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
		final CoreApplication app = new CoreApplication(DemoScene2D.class);
		app.run();
	}
}
