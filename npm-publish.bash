#!/bin/bash

set -o pipefail

declare Pkg=npm-publish
declare Version=0.2.0

function msg() {
    echo "$Pkg: $*"
}

function err() {
    msg "$*" 1>&2
}

function main() {
    local module_version=$1
    if [[ ! $module_version ]]; then
        err "first parameter must be the version number of the module to publish"
        return 10
    fi

    local build_dir="target/typescript"
    if [[ ! -d $build_dir ]]; then
        err "typescipt build directory does not exist: $build_dir"
        return 1
    fi

    if [[ $NPM_TOKEN ]]; then
        msg "creating local .npmrc using API key from environment"
        if ! ( umask 077 && echo "//registry.npmjs.org/:_authToken=$NPM_TOKEN" > "$HOME/.npmrc" ); then
            err "failed to create $HOME/.npmrc"
            return 1
        fi
    else
        msg "assuming your .npmrc is setup correctly for this project"
    fi

    # npm honors this
    rm -f "$build_dir/.gitignore"

    if ! ( cd "$build_dir" && npm publish --access=public ); then
        err "failed to publish node module"
        cat "$build_dir/npm-debug.log"
        return 1
    fi
}

main "$@" || exit 1
exit 0
