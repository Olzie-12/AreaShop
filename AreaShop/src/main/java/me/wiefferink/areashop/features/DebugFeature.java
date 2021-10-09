package me.wiefferink.areashop.features;

import me.wiefferink.areashop.AreaShopPlugin;
import me.wiefferink.areashop.events.ask.BuyingRegionEvent;
import me.wiefferink.areashop.events.ask.RentingRegionEvent;
import me.wiefferink.areashop.events.ask.ResellingRegionEvent;
import me.wiefferink.areashop.events.ask.SellingRegionEvent;
import me.wiefferink.areashop.events.ask.UnrentingRegionEvent;
import me.wiefferink.areashop.events.askandnotify.AddedFriendEvent;
import me.wiefferink.areashop.events.askandnotify.DeletedFriendEvent;
import me.wiefferink.areashop.events.notify.AddedRegionEvent;
import me.wiefferink.areashop.events.notify.BoughtRegionEvent;
import me.wiefferink.areashop.events.notify.DeletedRegionEvent;
import me.wiefferink.areashop.events.notify.RentedRegionEvent;
import me.wiefferink.areashop.events.notify.ResoldRegionEvent;
import me.wiefferink.areashop.events.notify.SoldRegionEvent;
import me.wiefferink.areashop.events.notify.UnrentedRegionEvent;
import me.wiefferink.areashop.events.notify.UpdateRegionEvent;
import me.wiefferink.areashop.tools.Utils;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DebugFeature extends RegionFeature {

	@Inject
	DebugFeature(@Nonnull AreaShopPlugin plugin) {
		super(plugin);
	}

	@EventHandler
	public void buyingRegion(BuyingRegionEvent event) {
		AreaShopPlugin.debug("BuyingRegionEvent: " + event.getPlayer().getName() + " is trying to buy " + event.getRegion().getName());
	}

	@EventHandler
	public void boughtRegion(BoughtRegionEvent event) {
		AreaShopPlugin.debug("BoughtRegionEvent: " + event.getRegion().getPlayerName() + " bought " + event.getRegion().getName());
	}

	@EventHandler
	public void sellingRegion(SellingRegionEvent event) {
		AreaShopPlugin.debug("SellingRegionEvent: " + event.getRegion().getName() + " is trying to sell " + event.getRegion().getName());
	}

	@EventHandler
	public void soldRegion(SoldRegionEvent event) {
		AreaShopPlugin.debug("SoldRegionEvent: " + Utils.toName(event.getOldBuyer()) + " sold " + event.getRegion().getName());
	}

	@EventHandler
	public void resellingRegion(ResellingRegionEvent event) {
		AreaShopPlugin.debug("ResellingRegionEvent: " + event.getRegion().getName() + " is trying to resell " + event.getRegion().getName());
	}

	@EventHandler
	public void resoldRegion(ResoldRegionEvent event) {
		AreaShopPlugin.debug("ResoldRegionEvent: " + Utils.toName(event.getFromPlayer()) + " resold " + event.getRegion().getName() + " to " + event.getRegion().getPlayerName());
	}

	@EventHandler
	public void rentingRegion(RentingRegionEvent event) {
		AreaShopPlugin.debug("RentingRegionEvent: " + event.getPlayer().getName() + " is trying to rent " + event.getRegion().getName() + ", extending=" + event.isExtending());
	}

	@EventHandler
	public void rentedRegion(RentedRegionEvent event) {
		AreaShopPlugin.debug("RentedRegionEvent: " + event.getRegion().getPlayerName() + " rented " + event.getRegion().getName() + ", extending=" + event.hasExtended());
	}

	@EventHandler
	public void unrentingRegion(UnrentingRegionEvent event) {
		AreaShopPlugin.debug("UnrentingRegionEvent: " + event.getRegion().getPlayerName() + " is trying to unrent " + event.getRegion().getName());
	}

	@EventHandler
	public void unrentedRegion(UnrentedRegionEvent event) {
		AreaShopPlugin.debug("UnrentedRegionEvent: " + Utils.toName(event.getOldRenter()) + " unrented " + event.getRegion().getName());
	}

	@EventHandler
	public void regionUpdate(UpdateRegionEvent event) {
		//AreaShop.debug("UpdateRegionEvent: " + event.getRegion().getName() + " updated");
	}

	@EventHandler
	public void addedRegion(AddedRegionEvent event) {
		AreaShopPlugin.debug("AddedRegionEvent: " + event.getRegion().getName());
	}

	@EventHandler
	public void removedRegion(DeletedRegionEvent event) {
		AreaShopPlugin.debug("DeletedRegionEvent: " + event.getRegion().getName());
	}

	@EventHandler
	public void addedFriend(AddedFriendEvent event) {
		AreaShopPlugin.debug("AddedFriendEvent: region " + event.getRegion().getName() + ", " + event.getFriend().getName() + " by " + (event.getBy() == null ? "<nobody>" : event.getBy().getName()));
	}

	@EventHandler
	public void deleteFriend(DeletedFriendEvent event) {
		AreaShopPlugin.debug("DeletedFriendEvent: region " + event.getRegion().getName() + ", " + event.getFriend().getName() + " by " + (event.getBy() == null ? "<nobody>" : event.getBy().getName()));
	}
}
