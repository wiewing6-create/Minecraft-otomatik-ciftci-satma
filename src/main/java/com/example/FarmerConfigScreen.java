package com.example;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class FarmerConfigScreen extends Screen {
    private TextFieldWidget commandField;
    private TextFieldWidget configField;

    public FarmerConfigScreen() {
        super(Text.literal("Farmer Paneli"));
    }

    @Override
    protected void init() {

        // sunucunuzdaki çiftçiyi açmak için kullandığınız komutu yazın
        this.commandField = new TextFieldWidget(textRenderer, width / 2 - 100, 60, 200, 20, Text.literal("Komut"));
        this.commandField.setMaxLength(256);
        this.commandField.setText(FarmerSettings.command);
        this.addDrawableChild(this.commandField);

        // kısa şekilde ne işe yaradığını anlatacağım sonrasında nasıl çalıştırabileceğinizi anlatacağım. Buraya gui üzerindeki hangi sayfalarda hangi slotların satılacağı bilgisini gireceksiniz. Çalışma mantığı ise şöyle: siz config kısmında örnek olarak "Page: 1, slot: 15,16,17" şeklinde bir bilgi girdiğinizde otomatik olarak çiftçi satma gui'ını açacak ve 1. sayfada (yani ilk açılan sayfaya) 15,16,17. slotları satacak. Eğer page: 2, slot: 10,11,12 şeklinde bir bilgi girerseniz ilk açılan sayfadan sonra son slotta duran "2. sayfaya geç" butonuna basacak ve girdiğiniz slotları satacak. (fakat bazı durumlarda 2. sayfaya geçme butonu son slotta değilde başka yerlerdede bulunabiliyor yada kendi kendine bozulabiliyor. Bu yüzden page sistemi yerine slot sistemini kullanarak sayfa atlayıp sayfa çeviriniz)
        this.configField = new TextFieldWidget(textRenderer, width / 2 - 100, 100, 200, 20, Text.literal("Config"));
        this.configField.setMaxLength(4096);
        this.configField.setText(FarmerSettings.rawConfig);
        this.addDrawableChild(this.configField);
        // başlatma butonu, configi otomatik olarak kaydediyor.
        this.addDrawableChild(ButtonWidget.builder(Text.literal("KAYDET VE BASLAT"), button -> {
            FarmerSettings.command = commandField.getText();
            FarmerSettings.rawConfig = configField.getText();
            FarmerSettings.isRunning = true;
            FarmerSettings.currentStep = 0; 
            FarmerSettings.save();
            this.close();
        }).dimensions(width / 2 - 100, 140, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        this.renderBackground(context, mouseX, mouseY, delta);
        
        super.render(context, mouseX, mouseY, delta);
    }
}