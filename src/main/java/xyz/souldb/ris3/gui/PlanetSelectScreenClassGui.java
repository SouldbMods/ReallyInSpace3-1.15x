
package xyz.souldb.ris3.gui;

import xyz.souldb.ris3.Ris3ModElements;
import xyz.souldb.ris3.Ris3Mod;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.network.PacketBuffer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@Ris3ModElements.ModElement.Tag
public class PlanetSelectScreenClassGui extends Ris3ModElements.ModElement {
	public static HashMap guistate = new HashMap();
	private static ContainerType<GuiContainerMod> containerType = null;
	public PlanetSelectScreenClassGui(Ris3ModElements instance) {
		super(instance, 1);
		elements.addNetworkMessage(ButtonPressedMessage.class, ButtonPressedMessage::buffer, ButtonPressedMessage::new,
				ButtonPressedMessage::handler);
		elements.addNetworkMessage(GUISlotChangedMessage.class, GUISlotChangedMessage::buffer, GUISlotChangedMessage::new,
				GUISlotChangedMessage::handler);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, GuiWindow::new));
	}

	@SubscribeEvent
	public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(containerType.setRegistryName("planet_select_screen_class"));
	}
	public static class GuiContainerModFactory implements IContainerFactory {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainerMod(id, inv, extraData);
		}
	}

	public static class GuiContainerMod extends Container implements Supplier<Map<Integer, Slot>> {
		private World world;
		private PlayerEntity entity;
		private int x, y, z;
		private IItemHandler internal;
		private Map<Integer, Slot> customSlots = new HashMap<>();
		private boolean bound = false;
		public GuiContainerMod(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(containerType, id);
			this.entity = inv.player;
			this.world = inv.player.world;
			this.internal = new ItemStackHandler(0);
			BlockPos pos = null;
			if (extraData != null) {
				pos = extraData.readBlockPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
			}
		}

		public Map<Integer, Slot> get() {
			return customSlots;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return true;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class GuiWindow extends ContainerScreen<GuiContainerMod> {
		private World world;
		private int x, y, z;
		private PlayerEntity entity;
		TextFieldWidget SearchPlanets;
		public GuiWindow(GuiContainerMod container, PlayerInventory inventory, ITextComponent text) {
			super(container, inventory, text);
			this.world = container.world;
			this.x = container.x;
			this.y = container.y;
			this.z = container.z;
			this.entity = container.entity;
			this.xSize = 431;
			this.ySize = 247;
		}

		@Override
		public void render(int mouseX, int mouseY, float partialTicks) {
			this.renderBackground();
			super.render(mouseX, mouseY, partialTicks);
			this.renderHoveredToolTip(mouseX, mouseY);
			SearchPlanets.render(mouseX, mouseY, partialTicks);
		}

		@Override
		protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
			GL11.glColor4f(1, 1, 1, 1);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/selbackround.png"));
			this.blit(this.guiLeft + -55, this.guiTop + -3, 0, 0, 854, 480, 854, 480);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/orbit2.png"));
			this.blit(this.guiLeft + 122, this.guiTop + 20, 0, 0, 200, 200, 200, 200);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/orbitone.png"));
			this.blit(this.guiLeft + 68, this.guiTop + -10, 0, 0, 256, 256, 256, 256);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/mars.png"));
			this.blit(this.guiLeft + 110, this.guiTop + 113, 0, 0, 16, 16, 16, 16);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/earth.png"));
			this.blit(this.guiLeft + 149, this.guiTop + 69, 0, 0, 16, 16, 16, 16);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/venusorbit.png"));
			this.blit(this.guiLeft + 137, this.guiTop + 35, 0, 0, 160, 160, 160, 160);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/venus.png"));
			this.blit(this.guiLeft + 207, this.guiTop + 52, 0, 0, 16, 16, 16, 16);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/mercorbitnew.png"));
			this.blit(this.guiLeft + 192, this.guiTop + 85, 0, 0, 64, 64, 64, 64);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/mercury.png"));
			this.blit(this.guiLeft + 199, this.guiTop + 88, 0, 0, 16, 16, 16, 16);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/sun.png"));
			this.blit(this.guiLeft + 217, this.guiTop + 110, 0, 0, 16, 16, 16, 16);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/earthorbitmoon1.png"));
			this.blit(this.guiLeft + 137, this.guiTop + 55, 0, 0, 48, 48, 48, 48);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/mooninclass.png"));
			this.blit(this.guiLeft + 159, this.guiTop + 54, 0, 0, 4, 4, 4, 4);
			Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation("ris3:textures/mooninclass.png"));
			this.blit(this.guiLeft + 153, this.guiTop + 100, 0, 0, 4, 4, 4, 4);
		}

		@Override
		public boolean keyPressed(int key, int b, int c) {
			if (key == 256) {
				this.minecraft.player.closeScreen();
				return true;
			}
			if (SearchPlanets.isFocused())
				return SearchPlanets.keyPressed(key, b, c);
			return super.keyPressed(key, b, c);
		}

		@Override
		public void tick() {
			super.tick();
			SearchPlanets.tick();
		}

		@Override
		protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
			this.font.drawString("Planets", 200, 5, -16711936);
			this.font.drawString("T11", 62, 36, -1);
			this.font.drawString("T3", 81, 66, -1);
			this.font.drawString("T2", 76, 93, -1);
			this.font.drawString("T1", 74, 121, -1);
			this.font.drawString("T1", 73, 153, -1);
		}

		@Override
		public void removed() {
			super.removed();
			Minecraft.getInstance().keyboardListener.enableRepeatEvents(false);
		}

		@Override
		public void init(Minecraft minecraft, int width, int height) {
			super.init(minecraft, width, height);
			minecraft.keyboardListener.enableRepeatEvents(true);
			this.addButton(new Button(this.guiLeft + 19, this.guiTop + 35, 40, 20, "Sun", e -> {
				Ris3Mod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(0, x, y, z));
				handleButtonAction(entity, 0, x, y, z);
			}));
			this.addButton(new Button(this.guiLeft + 19, this.guiTop + 61, 60, 20, "Mercury", e -> {
				Ris3Mod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(1, x, y, z));
				handleButtonAction(entity, 1, x, y, z);
			}));
			this.addButton(new Button(this.guiLeft + 20, this.guiTop + 91, 50, 20, "Venus", e -> {
				Ris3Mod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(2, x, y, z));
				handleButtonAction(entity, 2, x, y, z);
			}));
			this.addButton(new Button(this.guiLeft + 20, this.guiTop + 121, 50, 20, "Earth", e -> {
				Ris3Mod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(3, x, y, z));
				handleButtonAction(entity, 3, x, y, z);
			}));
			this.addButton(new Button(this.guiLeft + 20, this.guiTop + 151, 45, 20, "Mars", e -> {
				Ris3Mod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(4, x, y, z));
				handleButtonAction(entity, 4, x, y, z);
			}));
			this.addButton(new Button(this.guiLeft + 343, this.guiTop + 223, 85, 20, "Next Page >>", e -> {
				Ris3Mod.PACKET_HANDLER.sendToServer(new ButtonPressedMessage(5, x, y, z));
				handleButtonAction(entity, 5, x, y, z);
			}));
			SearchPlanets = new TextFieldWidget(this.font, this.guiLeft + 305, this.guiTop + 8, 120, 20, "Search Planets...") {
				{
					setSuggestion("Search Planets...");
				}
				@Override
				public void writeText(String text) {
					super.writeText(text);
					if (getText().isEmpty())
						setSuggestion("Search Planets...");
					else
						setSuggestion(null);
				}

				@Override
				public void setCursorPosition(int pos) {
					super.setCursorPosition(pos);
					if (getText().isEmpty())
						setSuggestion("Search Planets...");
					else
						setSuggestion(null);
				}
			};
			guistate.put("text:SearchPlanets", SearchPlanets);
			SearchPlanets.setMaxStringLength(32767);
			this.children.add(this.SearchPlanets);
		}
	}

	public static class ButtonPressedMessage {
		int buttonID, x, y, z;
		public ButtonPressedMessage(PacketBuffer buffer) {
			this.buttonID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
		}

		public ButtonPressedMessage(int buttonID, int x, int y, int z) {
			this.buttonID = buttonID;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.buttonID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
		}

		public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	public static class GUISlotChangedMessage {
		int slotID, x, y, z, changeType, meta;
		public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
			this.slotID = slotID;
			this.x = x;
			this.y = y;
			this.z = z;
			this.changeType = changeType;
			this.meta = meta;
		}

		public GUISlotChangedMessage(PacketBuffer buffer) {
			this.slotID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
			this.changeType = buffer.readInt();
			this.meta = buffer.readInt();
		}

		public static void buffer(GUISlotChangedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.slotID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
			buffer.writeInt(message.changeType);
			buffer.writeInt(message.meta);
		}

		public static void handler(GUISlotChangedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int slotID = message.slotID;
				int changeType = message.changeType;
				int meta = message.meta;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleSlotAction(entity, slotID, changeType, meta, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}
	private static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}

	private static void handleSlotAction(PlayerEntity entity, int slotID, int changeType, int meta, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}
}
