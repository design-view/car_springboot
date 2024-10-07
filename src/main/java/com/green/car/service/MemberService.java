package com.green.car.service;

import com.green.car.constant.Role;
import com.green.car.dto.MemberDto;
import com.green.car.dto.MemberJoinDto;
import com.green.car.dto.TokenDto;
import com.green.car.entity.Member;

import java.util.List;

public interface MemberService {
    //회원가입하기
    Member saveMember(MemberJoinDto dto);
    //이메일체크
    String validateDuplicateMember(MemberJoinDto dto);
    //로그인하기
    TokenDto login(String memberId, String password);
    //회원목록조회
    List<MemberDto> getMemberList();
    //dto전달받아서 entity반환
    default Member dtoToEntity(MemberJoinDto dto){
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .role(Role.USER)
                .build();
        return member;
    }
    //entity전달 받아서 MemberDto로 반환
    default MemberDto entityToDto(Member member){
        MemberDto dto = MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .role(member.getRole())
                .dealerId(member.getDealerId())
                .build();
        return dto;
    }
}
