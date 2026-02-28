package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class FarmerAutoSellClient implements ClientModInitializer {
    private List<Integer> currentSlotsToClick = new ArrayList<>();
    private int currentTargetPage = 1;
    private boolean jKeyPressed = false;

    @Override
    public void onInitializeClient() {
        FarmerSettings.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.getWindow() == null) return;

            //ben j tuşunu tercih ettim modun config kısmının açılması için fakat siz istediğiniz gibi değiştirebilirsiniz.
            boolean isJDown = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_J) == GLFW.GLFW_PRESS;
            
            if (isJDown && !jKeyPressed) { 
                jKeyPressed = true;
                if (FarmerSettings.isRunning) {
                    FarmerSettings.isRunning = false;
                    currentSlotsToClick.clear();
                    client.player.sendMessage(Text.literal("§c[Farmer] Otomasyon DURDURULDU!"), false);
                } else {
                    client.setScreen(new FarmerConfigScreen());
                }
            } else if (!isJDown) {
                jKeyPressed = false;
            }

            // her seferinde gui'i açık olup olmadığını kontrol ediyor ve kapalıysa kendi kendine tekrar açıyor (esc basarak denemeyin kapatmayı). Eğer otomatik satmayı kapatmak istiyorsanız config ekranını açmak için kullandığınız butona basarak kapatacaksınız(eğer j yerine başka birşey koymuşunuzdur diye söylüyorum)
            if (FarmerSettings.isRunning) {
                if (FarmerSettings.tickDelay > 0) {
                    FarmerSettings.tickDelay--;
                    return;
                }

                if (!(client.currentScreen instanceof GenericContainerScreen)) {
                    client.getNetworkHandler().sendChatCommand(FarmerSettings.command.replace("/", ""));
                    FarmerSettings.tickDelay = 30;
                    FarmerSettings.updatePageList();
                    FarmerSettings.currentPageIndex = 0;
                    if (!FarmerSettings.targetPages.isEmpty()) {
                        currentTargetPage = FarmerSettings.targetPages.get(0);
                    }
                    return;
                }

                GenericContainerScreen screen = (GenericContainerScreen) client.currentScreen;
                int syncId = screen.getScreenHandler().syncId;

                if (currentSlotsToClick.isEmpty()) {
                    currentSlotsToClick.addAll(FarmerSettings.getSlotsForPage(currentTargetPage));
                }

                if (!currentSlotsToClick.isEmpty()) {
                    int slot = currentSlotsToClick.remove(0);
                    // Oynadığınız sunucularda çoğunlukla shift+sağ tık ile satma işlemi yaparsınız, fakat eğer farklıysa değiştirebilirsiniz.
                    client.interactionManager.clickSlot(syncId, slot, 1, SlotActionType.QUICK_MOVE, client.player);
                    FarmerSettings.tickDelay = 15;
                } else {
                    FarmerSettings.currentPageIndex++;
                    if (FarmerSettings.currentPageIndex < FarmerSettings.targetPages.size()) {
                        currentTargetPage = FarmerSettings.targetPages.get(FarmerSettings.currentPageIndex);
                        // Sonraki sayfa butonu (Slot 53) bu "page" kısmına çok güvenmemenizi rica ediyorum dediğim gibi bazen sıkıntı çıkartabiliyor siz en iyisi sayfa değiştirecekseniz slot ile değiştirin.
                        client.interactionManager.clickSlot(syncId, 53, 0, SlotActionType.PICKUP, client.player);
                        FarmerSettings.tickDelay = 25;
                    } else {
                        FarmerSettings.isRunning = false;
                        client.player.sendMessage(Text.literal("§6[Farmer] Satış tamamlandı!"), false);
                    }
                }
            }
        });
    }
}