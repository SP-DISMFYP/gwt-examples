package com.gonevertical.client.views.widgets;

import java.util.Iterator;
import java.util.List;

import org.gonevertical.core.client.input.WiseTextBox;

import com.gonevertical.client.app.ApplicationFactory;
import com.gonevertical.client.app.activity.places.WalletEditPlace;
import com.gonevertical.client.app.requestfactory.dto.WalletDataProxy;
import com.gonevertical.client.app.requestfactory.dto.WalletItemDataProxy;
import com.gonevertical.client.global.booleandialog.BooleanDialog;
import com.gonevertical.client.global.booleandialog.BooleanEvent;
import com.gonevertical.client.global.booleandialog.BooleanEvent.Selected;
import com.gonevertical.client.global.booleandialog.BooleanEventHandler;
import com.gonevertical.client.views.WalletListView.Presenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.gwt.event.dom.client.ChangeEvent;

public class WalletListItemWidget extends Composite {

  /**
   * widget state View | Edit
   */
  public static enum State {
    VIEW, EDIT;
  }
  private State stateIs;

  private Presenter presenter;

  private ApplicationFactory appFactory;

  private static WalletListItemWidgetUiBinder uiBinder = GWT.create(WalletListItemWidgetUiBinder.class);
  @UiField WiseTextBox tbName;
  @UiField FocusPanel pFocus;
  @UiField PushButton bDelete;
  @UiField PushButton bView;

  private WalletDataProxy walletData;

  private BooleanDialog wconfirm;

  private int index;

  interface WalletListItemWidgetUiBinder extends UiBinder<Widget, WalletListItemWidget> {
  }

  public WalletListItemWidget() {
    initWidget(uiBinder.createAndBindUi(this));
    
    setState(State.VIEW);
  }

  public void setData(int index, WalletDataProxy walletDataProxy) {
    this.index = index;
    // TODO set style depending on i
    this.walletData = walletDataProxy; 
  }

  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  public void setAppFactory(ApplicationFactory appFactory) {
    this.appFactory = appFactory;
  }

  public void draw() {

    setState(State.VIEW);

    drawName();
  }

  private void drawName() {
    if (walletData == null || 
        walletData.getName() == null || 
        walletData.getName().trim().length() == 0) {
      String s = index + " Item in wallet";
      tbName.setText(s);
      return;
    }
    
    String s = walletData.getName();
    SafeHtml sh = SimpleHtmlSanitizer.sanitizeHtml(s);
    tbName.setText(sh.asString());
   
  }

  public void edit() {

    // goto to edit view
    presenter.goTo(new WalletEditPlace(walletData));
    
  }

  private void setNameData() {
    if (walletData == null) {
      // this shouldn't happen
      return;
    }
    walletData.setName(getName());
  }

  private String getName() {
    String s = tbName.getText().trim();
    if (s.length() == 0) {
      s = null;
    }
    return s;
  }

  private void save() {
    Request<WalletDataProxy> req = appFactory.getRequestFactory().getWalletDataRequest().persist().using(walletData);
    req.fire(new Receiver<WalletDataProxy>() {
      public void onSuccess(WalletDataProxy walletData) {
        process(walletData);
      }
      public void onFailure(ServerFailure error) {
        super.onFailure(error);
      }
    });
  }
  
  private void process(WalletDataProxy walletData) {
    this.walletData = walletData;
  }

  private void delete() {
    if (wconfirm == null) {
      wconfirm = new BooleanDialog("Are you sure you want to delete this?");
      wconfirm.addSelectionHandler(new BooleanEventHandler() {
        public void onBooleanEvent(BooleanEvent event) {
          if (event.getBooleanEvent() == Selected.TRUE) {
            deleteIt();
          } else if (event.getBooleanEvent() == Selected.FALSE) {
            // do nothing
          }
        }
      });
    }
    wconfirm.center();
  }

  private void deleteIt() {
    if (walletData == null || walletData.getId() == null) {
      removeFromParent();
      return;
    }
    Request<Boolean> req = appFactory.getRequestFactory().getWalletDataRequest().deleteWalletData(walletData.getId());
    req.fire(new Receiver<Boolean>() {
      public void onSuccess(Boolean data) {
        if (data != null && data.booleanValue() == true) {
          removeFromParent();
        } else {
          // TODO show error
        }
      }
      public void onFailure(ServerFailure error) {
        super.onFailure(error);
      }
    });
  }

  private void setState(State state) {
    stateIs = state;
    if (state == State.VIEW) {
      setStateView();
    } else if (state == State.EDIT) {
      setStateEdit();
    }
  }

  private void setStateView() {
    tbName.setEdit(false);
  }

  private void setStateEdit() {
    tbName.setEdit(true);
  }

  @UiHandler("tbName")
  public void onTbNameTouchStart(TouchStartEvent event) {
    if (stateIs == State.VIEW) {
      setState(State.EDIT);
    } else if (stateIs == State.EDIT) {
      setState(State.VIEW);
    }
  }

  @UiHandler("tbName")
  public void onTbNameTouchEnd(TouchEndEvent event) {
  }

  @UiHandler("tbName")
  public void onTbNameMouseOver(MouseOverEvent event) {
    setState(State.EDIT);
  }

  @UiHandler("tbName")
  public void onTbNameMouseOut(MouseOutEvent event) {
    
    setState(State.VIEW);
  }

  @UiHandler("tbName")
  void onTbNameChange(ChangeEvent event) {
    setNameData();
    save();
    drawName();
  }

  @UiHandler("bDelete")
  public void onBDeleteClick(ClickEvent event) {
    delete();
  }

  @UiHandler("bView")
  public void onBViewClick(ClickEvent event) {
    edit();
  }

}
