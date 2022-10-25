package com.elos.webquizengine.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime date;

    @Valid
    @NotNull
    @NotEmpty
    @Size(min=2)
    private ArrayList<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ArrayList<Integer> answer;

}
