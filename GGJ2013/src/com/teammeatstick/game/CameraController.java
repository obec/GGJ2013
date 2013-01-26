package com.teammeatstick.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraController {
	private OrthographicCamera _camera;
	
	CameraController(float width, float height){
		_camera = new OrthographicCamera();
		_camera.setToOrtho(false, width, height);
	}
}
