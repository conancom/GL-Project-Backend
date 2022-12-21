package com.projectgl.backend.Response;

import lombok.*;

import java.util.ArrayList;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IgdbGameResponse{

    private int id;

    private int cover;

    private int first_release_date;

    private String name;

    private ArrayList<Integer> screenshots;

    private ArrayList<Integer> artworks;

    private ArrayList<Integer> videos;

    private double rating;

    private String storyline;

    private String summary;
}
