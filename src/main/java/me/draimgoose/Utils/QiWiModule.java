package me.draimgoose.Utils;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import com.qiwi.billpayments.sdk.web.ApacheWebClient;
import me.draimgoose.Config.MainConfig;
import me.draimgoose.DB.DBWrite;
import me.draimgoose.DraimDonate;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.UUID;

public class QiWiModule
{
    public static BillPaymentClient client;
    private static HashMap<UUID, String> clients;
    public static HashMap<UUID, String> getClients() {
        return QiWiModule.clients;
    }

    public static void generateBill(final Player p, final int sum) {
        if(!getClients().containsKey(p.getUniqueId())) {
             final CreateBillInfo billInfo = new CreateBillInfo(UUID.randomUUID().toString(), new MoneyAmount(BigDecimal.valueOf(sum), Currency.getInstance(MainConfig.getMain().getCFG().getString("DraimDonate.Currency"))), "Пополнение баланса игрока " + p.getName(), ZonedDateTime.now().plusHours(1L), new Customer(MainConfig.getMain().getCFG().getString("DraimDonate.Email"), UUID.randomUUID().toString(), MainConfig.getMain().getCFG().getString("DraimDonate.Phone")), MainConfig.getMain().getCFG().getString("DraimDonate.Site"));
             try {
                final BillResponse response = QiWiModule.client.createBill(billInfo);
                QiWiModule.clients.put(p.getUniqueId(), response.getBillId());
                String url = response.getPayUrl();
                String msg = UsefulFunc.config("messages","Messages.Json.Message", p, sum);
                String msg2 = UsefulFunc.config("messages","Messages.Json.Message2", p, sum);
                String msgBorders = UsefulFunc.config("messages","Messages.Json.Message3", p, sum);
                p.sendMessage(msgBorders);
                p.spigot().sendMessage(new ComponentBuilder(msg)
                        .event(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(UsefulFunc.config("messages","Messages.Json.Message4", p, sum))))
                        .create());
                p.sendMessage(msg2);
                p.sendMessage(msgBorders);
            }
            catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
        else {
            UsefulFunc.sendUsefulMSG(p, "Messages.Another.ActiveBill");
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_STEP, 1.0f, 1.0f);
        }
    }

    public static void onPaid(final Player p) {
        final BillResponse response = QiWiModule.client.getBillInfo(getClients().get(p.getUniqueId()));
        final int amount = response.getAmount().getValue().intValue() * MainConfig.getMain().getCFG().getInt("DraimDonate.Multiplication");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MainConfig.getMain().getCFG().getString("DraimDonate.Command").replace("%player%", p.getName()).replace("%amount%", "" + amount));
        getClients().remove(p.getUniqueId());
        UsefulFunc.sendUsefulMSG(p, "Messages.Status.Paid");
        DBWrite.addPlayerDonate(p, amount);
        SoundUtils.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
        UsefulFunc.sendLog(ChatColor.LIGHT_PURPLE + p.getName() + " "+UsefulFunc.config("messages","Messages.Console.Message", p, amount));
    }

    public static void checkBill(final Player p) {
        final BillResponse response = QiWiModule.client.getBillInfo(getClients().get(p.getUniqueId()));
        switch (response.getStatus().getValue()) {
            case PAID: {
                QiWiModule.onPaid(p);
                break;
            }
            case WAITING: {
                UsefulFunc.sendUsefulMSG(p, "Messages.Status.Waiting");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
                break;
            }
            case REJECTED: {
                UsefulFunc.sendUsefulMSG(p, "Messages.Status.Rejected");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
                break;
            }
            case EXPIRED: {
                UsefulFunc.sendUsefulMSG(p, "Messages.Status.Expired");
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0f, 1.0f);
                break;
            }
        }
    }
    static {
        QiWiModule.client = BillPaymentClientFactory.createCustom(DraimDonate.getQiwi(), new ApacheWebClient(HttpClients.createDefault()));
        QiWiModule.clients = new HashMap<UUID, String>();
    }
}
