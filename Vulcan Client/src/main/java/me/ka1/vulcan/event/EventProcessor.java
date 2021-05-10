package me.ka1.vulcan.event;

import com.google.common.collect.Maps;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.command.Command;
import me.ka1.vulcan.command.CommandManager;
import me.ka1.vulcan.event.events.PacketEvent;
import me.ka1.vulcan.event.events.PlayerJoinEvent;
import me.ka1.vulcan.event.events.PlayerLeaveEvent;
import me.ka1.vulcan.module.ModuleManager;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class EventProcessor {

    public static EventProcessor INSTANCE;
    Minecraft mc = Minecraft.getMinecraft();
    CommandManager commandManager = new CommandManager();
    float hue = 0;
    Color c;
    int rgb;
    int speed = 2;

    public EventProcessor(){
        INSTANCE = this;
    }

    public int getRgb(){
        return rgb;
    }
    public Color getC(){
        return c;
    }
    public void setRainbowSpeed(int s){
        speed = s;
    }
    public int getRainbowSpeed(){
        return speed;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        //rainbow reee
        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};

        int rgb = Color.HSBtoRGB(hue[0], 1f, 1f);

        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        hue[0] += .02f;

        c = new Color(red,green,blue);

        //Module updates
        // #TO CYBER: DONT DELETE THIS AGAIN BY ACCIDENT DUMBASS
        if (mc.player != null)
            ModuleManager.onUpdate();
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        ModuleManager.onWorldRender(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        Vulcan.EVENT_BUS.post(event);
        if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            //module onRender
            ModuleManager.onRender();
            //HudComponent stuff
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if(Keyboard.getEventKey() == 0 || Keyboard.getEventKey() == Keyboard.KEY_NONE) return;
            ModuleManager.onBind(Keyboard.getEventKey());
            //Macro
            Vulcan.getInstance().macroManager.getMacros().forEach(m -> {
                if(m.getKey() == Keyboard.getEventKey())
                    m.onMacro();
            });
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event){
        if(Mouse.getEventButtonState())
            Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {

        if (event.getMessage().startsWith(Command.getPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                commandManager.callCommand(event.getMessage().substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendClientMessage(ChatFormatting.DARK_RED + "Error: " + e.getMessage());
            }
            //event.setMessage("");
        }
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event){ Vulcan.EVENT_BUS.post(event); }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event){
        Vulcan.EVENT_BUS.post(event);
    }
    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event){
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        Vulcan.EVENT_BUS.post(event);}

    @SubscribeEvent
    public void onPlayerPush(PlayerSPPushOutOfBlocksEvent event) {
        Vulcan.EVENT_BUS.post(event);}

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        Vulcan.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        Vulcan.EVENT_BUS.post(event);
    }

    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketPlayerListItem) {
            SPacketPlayerListItem packet = (SPacketPlayerListItem) event.getPacket();
            if (packet.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != mc.session.getProfile().getId()) {
                        new Thread(() -> {
                            String name = resolveName(playerData.getProfile().getId().toString());
                            if (name != null) {
                                if (mc.player != null && mc.player.ticksExisted >= 1000)
                                    Vulcan.EVENT_BUS.post(new PlayerJoinEvent(name));
                            }
                        }).start();
                    }
                }
            }
            if (packet.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != mc.session.getProfile().getId()) {
                        new Thread(() -> {
                            final String name = resolveName(playerData.getProfile().getId().toString());
                            if (name != null) {
                                if (mc.player != null && mc.player.ticksExisted >= 1000)
                                    Vulcan.EVENT_BUS.post(new PlayerLeaveEvent(name));
                            }
                        }).start();
                    }
                }
            }
        }
    });

    private final Map<String, String> uuidNameCache = Maps.newConcurrentMap();

    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (uuidNameCache.containsKey(uuid)) {
            return uuidNameCache.get(uuid);
        }

        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray) JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject) jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void init(){
        Vulcan.EVENT_BUS.subscribe(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
}

