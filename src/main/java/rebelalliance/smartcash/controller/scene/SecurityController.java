package rebelalliance.smartcash.controller.scene;

import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;

public class SecurityController extends BaseController implements IController {
    @Override
    public void init() {
        this.addHeader();
    }
}
