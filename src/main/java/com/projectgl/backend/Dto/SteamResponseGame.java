package com.projectgl.backend.Dto;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SteamResponseGame {

    private int appid;

    private String name;

    private int playtime_forever;

    private String img_icon_url;

    private boolean has_community_visible_stats;

    private int playtime_windows_forever;

    private int playtime_mac_forever;

    private int playtime_linux_forever;

    private int rtime_last_played;
}
