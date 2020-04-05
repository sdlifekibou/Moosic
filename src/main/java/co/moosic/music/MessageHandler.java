package co.moosic.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;


public class MessageHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().getContentRaw().toLowerCase().startsWith(Config.command_prefix.toLowerCase() + "np")) {
            AudioTrack PlayingTrack = Login.scheduler.player.getPlayingTrack();
            e.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor("부르는 노래", PlayingTrack.getInfo().uri, null)
                    .setColor(Color.GREEN)
                    .addField("노래 이름", PlayingTrack.getInfo().title, true)
                    .addField("채널", PlayingTrack.getInfo().author, true)
                    .addField("노래 진행도", String.format("`%s / %s`", this.getLength(PlayingTrack.getPosition()), this.getLength(PlayingTrack.getInfo().length)), true)
                    .addField("노래 링크", "[유튜브 링크](" + PlayingTrack.getInfo().uri + ")", true)
                    .setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", PlayingTrack.getInfo().identifier))
                    .build()
            ).queue();
        }
        if (e.getMessage().getContentRaw().toLowerCase().startsWith(Config.command_prefix.toLowerCase() + "skip")) {
          nextTrack();
          AudioTrack PlayingTrack = Login.scheduler.player.getPlayingTrack();
          e.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor("새롭게 부르는 노래", PlayingTrack.getInfo().uri, null)
                    .setColor(Color.GREEN)
                    .addField("노래 이름", PlayingTrack.getInfo().title, true)
                    .addField("채널", PlayingTrack.getInfo().author, true)
                    .addField("노래 길이", String.format("`%s / %s`", this.getLength(PlayingTrack.getPosition()), this.getLength(PlayingTrack.getInfo().length)), true)
                    .addField("노래 링크", "[유튜브 링크](" + PlayingTrack.getInfo().uri + ")", true)
                    .setThumbnail(String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", PlayingTrack.getInfo().identifier))
                    .build()
          ).queue();
        }
    }

    private String getLength(long length) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(length),
                TimeUnit.MILLISECONDS.toSeconds(length) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(length))
        );
    }
}
