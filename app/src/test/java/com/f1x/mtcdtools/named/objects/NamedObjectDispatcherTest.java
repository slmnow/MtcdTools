package com.f1x.mtcdtools.named.objects;

import android.content.Context;
import android.content.Intent;

import com.f1x.mtcdtools.named.objects.actions.KeyAction;
import com.f1x.mtcdtools.storage.NamedObjectsStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by f1x on 2017-02-05.
 */

@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest({NamedObjectDispatcher.class})
@RunWith(PowerMockRunner.class)
public class NamedObjectDispatcherTest {
    @Before
    public void init() throws Exception {
        initMocks(this);
    }

    @Test
    public void test_ActionDispatch() {
        KeyAction action = mock(KeyAction.class);
        NamedObjectId actionId = new NamedObjectId("testActionName");
        when(action.getId()).thenReturn(actionId);
        when(action.getObjectType()).thenReturn(KeyAction.OBJECT_TYPE);

        when(mMockNamedObjectsStorage.getItem(actionId)).thenReturn(action);

        NamedObjectDispatcher dispatcher = new NamedObjectDispatcher(mMockNamedObjectsStorage);
        dispatcher.dispatch(actionId, mMockContext);
        verify(action, times(1)).evaluate(mMockContext);
    }

    @Test
    public void test_ActionsListDispatch() throws Exception {
        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mMockIntent);

        NamedObjectId actionsListId = new NamedObjectId("actionsListName");
        ActionsList actionsList = mock(ActionsList.class);
        when(actionsList.getId()).thenReturn(actionsListId);
        when(actionsList.getObjectType()).thenReturn(ActionsList.OBJECT_TYPE);

        when(mMockNamedObjectsStorage.getItem(actionsListId)).thenReturn(actionsList);

        NamedObjectDispatcher dispatcher = new NamedObjectDispatcher(mMockNamedObjectsStorage);
        dispatcher.dispatch(actionsListId, mMockContext);
        verify(mMockContext, times(1)).startActivity(mMockIntent);
    }

//    @Test
//    public void test_ActionsSequenceDispatch() throws Exception {
//        String actionsSequenceName = "actionsSequenceName";
//        ActionsSequence actionsSequence = mock(ActionsSequence.class);
//        when(actionsSequence.getName()).thenReturn(actionsSequenceName);
//        when(actionsSequence.getObjectType()).thenReturn(ActionsSequence.OBJECT_TYPE);
//
//        List<String> actionsNames = new ArrayList<>(Arrays.asList("action12", "action15", "action16"));
//        when(actionsSequence.getActionIds()).thenReturn(actionsNames);
//
//        Action action = mock(Action.class);
//
//        when(mMockNamedObjectsStorage.getItem(actionsSequenceName)).thenReturn(actionsSequence);
//        when(mMockNamedObjectsStorage.getItem(actionsNames.get(0))).thenReturn(action);
//        when(mMockNamedObjectsStorage.getItem(actionsNames.get(1))).thenReturn(action);
//        when(mMockNamedObjectsStorage.getItem(actionsNames.get(2))).thenReturn(action);
//
//        int executionDelay = 1234;
//        when(mMockConfiguration.getActionsSequenceDelay()).thenReturn(executionDelay);
//
//        NamedObjectsDispatchTask actionsDispatchTask = mock(NamedObjectsDispatchTask.class);
//        PowerMockito.whenNew(NamedObjectsDispatchTask.class).withArguments(executionDelay, mMockContext).thenReturn(actionsDispatchTask);
//
//        NamedObjectDispatcher dispatcher = new NamedObjectDispatcher(mMockNamedObjectsStorage, mMockConfiguration);
//        dispatcher.dispatch(actionsSequenceName, mMockContext);
//
//        verify(actionsDispatchTask, times(1)).execute();
//    }

    @Test
    public void test_Dispatch_NullObject() {
        NamedObjectDispatcher dispatcher = new NamedObjectDispatcher(mMockNamedObjectsStorage);

        NamedObjectId namedObjectId = new NamedObjectId("namedObjectName");
        when(mMockNamedObjectsStorage.getItem(namedObjectId)).thenReturn(null);
        dispatcher.dispatch(namedObjectId, mMockContext);
    }

    @Mock
    NamedObjectsStorage mMockNamedObjectsStorage;

    @Mock
    Intent mMockIntent;

    @Mock
    Context mMockContext;

    @Mock
    Intent mMockStartActivityIntent;
}
