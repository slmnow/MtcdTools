package com.f1x.mtcdtools.activities.actions;

import android.widget.Spinner;

import com.f1x.mtcdtools.R;
import com.f1x.mtcdtools.activities.NamedObjectActivity;
import com.f1x.mtcdtools.adapters.KeyCodesArrayAdapter;
import com.f1x.mtcdtools.named.objects.NamedObject;
import com.f1x.mtcdtools.named.objects.NamedObjectId;
import com.f1x.mtcdtools.named.objects.actions.KeyAction;

/**
 * Created by f1x on 2017-01-25.
 */

public class KeyActionActivity extends NamedObjectActivity {
    public KeyActionActivity() {
        super(R.layout.activity_key_action_details);
    }

    @Override
    protected void initControls() {
        super.initControls();

        mKeyCodesSpinner = (Spinner)this.findViewById(R.id.spinnerKeyCodes);
        mKeyCodesArrayAdapter = new KeyCodesArrayAdapter(this);
        mKeyCodesSpinner.setAdapter(mKeyCodesArrayAdapter);
    }

    @Override
    protected NamedObject createNamedObject(NamedObjectId namedObjectId) {
        String keyCodeName = (String)mKeyCodesSpinner.getSelectedItem();
        return new KeyAction(namedObjectId, mKeyCodesArrayAdapter.getKeyCode(keyCodeName));
    }

    @Override
    protected void fillControls(NamedObject namedObject) throws ClassCastException {
        super.fillControls(namedObject);

        KeyAction keyAction = (KeyAction)namedObject;
        int keyCodePosition = mKeyCodesArrayAdapter.getPosition(keyAction.getKeyCode());
        mKeyCodesSpinner.setSelection(keyCodePosition);
    }

    private KeyCodesArrayAdapter mKeyCodesArrayAdapter;
    private Spinner mKeyCodesSpinner;
}
